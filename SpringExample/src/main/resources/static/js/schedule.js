const scheduleListEl = document.getElementById('scheduleList');
const displayNameEl = document.getElementById('displayName');
const logoutButton = document.getElementById('logoutButton');

const scheduleForm = document.getElementById('scheduleForm');
const repeatForm = document.getElementById('repeatForm');
const editForm = document.getElementById('editForm');
const editCancelButton = document.getElementById('editCancelButton');

let scheduleCache = [];

document.addEventListener('DOMContentLoaded', async () => {
    const displayName = sessionStorage.getItem('displayName');
    const username = sessionStorage.getItem('username');

    if (!displayName && !username) {
        window.location.href = '/login.html';
        return;
    }

    displayNameEl.textContent = displayName || username || '-';

    bindEvents();
    await loadInitialData();
});

function bindEvents() {
    logoutButton.addEventListener('click', handleLogout);
    scheduleForm.addEventListener('submit', handleCreateSchedule);
    repeatForm.addEventListener('submit', handleCreateRepeatSchedule);
    editForm.addEventListener('submit', handleEditSchedule);
    editCancelButton.addEventListener('click', resetEditForm);

    scheduleListEl.addEventListener('click', async (event) => {
        const button = event.target.closest('button[data-action]');
        if (!button) return;

        const action = button.dataset.action;
        const scheduleId = Number(button.dataset.id);

        if (action === 'edit') {
            const target = scheduleCache.find(item => item.id === scheduleId);
            if (target) fillEditForm(target);
            return;
        }

        if (action === 'delete') {
            await handleDeleteSchedule(scheduleId);
            return;
        }

        if (action === 'toggle-important') {
            const currentImportant = button.dataset.important === 'true';
            await handleToggleImportant(scheduleId, !currentImportant);
        }
    });
}

async function loadInitialData() {
    await Promise.all([
        loadSummary(),
        loadSchedules()
    ]);
}

async function apiFetch(url, options = {}) {
    const response = await fetch(url, {
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
            ...(options.headers || {})
        },
        ...options
    });

    if (response.status === 401) {
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('displayName');
        alert('로그인이 만료되었거나 인증이 필요합니다.');
        window.location.href = '/login.html';
        throw new Error('Unauthorized');
    }

    const contentType = response.headers.get('content-type') || '';
    let data = null;

    if (contentType.includes('application/json')) {
        data = await response.json();
    }

    if (!response.ok) {
        const message = data && data.message ? data.message : '요청 처리 중 오류가 발생했습니다.';
        throw new Error(message);
    }

    return data;
}

async function loadSummary() {
    try {
        const summary = await apiFetch('/api/schedules/summary');

        document.getElementById('upcomingCount').textContent = summary?.upcomingCount ?? 0;
        document.getElementById('importantCount').textContent = summary?.importantCount ?? 0;
        document.getElementById('nearestTitle').textContent = summary?.nearestTitle || '-';
        document.getElementById('nearestStartAt').textContent = formatDateTime(summary?.nearestStartAt);
    } catch (error) {
        console.error(error);
    }
}

async function loadSchedules() {
    try {
        const schedules = await apiFetch('/api/schedules');
        scheduleCache = Array.isArray(schedules) ? schedules : [];
        renderSchedules(scheduleCache);
    } catch (error) {
        console.error(error);
    }
}

function renderSchedules(schedules) {
    if (!schedules.length) {
        scheduleListEl.innerHTML = '<p>등록된 일정이 없습니다.</p>';
        return;
    }

    scheduleListEl.innerHTML = schedules.map(schedule => `
        <div class="schedule-item">
            <div class="schedule-item-header">
                <div class="schedule-title">${escapeHtml(schedule.title || '')}</div>
                <span class="badge ${schedule.important ? 'badge-important' : 'badge-normal'}">
                    ${schedule.important ? '중요' : '일반'}
                </span>
            </div>

            <div class="schedule-meta"><strong>ID:</strong> ${schedule.id}</div>
            <div class="schedule-meta"><strong>설명:</strong> ${escapeHtml(schedule.description || '-')}</div>
            <div class="schedule-meta"><strong>시작:</strong> ${formatDateTime(schedule.startAt)}</div>
            <div class="schedule-meta"><strong>종료:</strong> ${formatDateTime(schedule.endAt)}</div>
            <div class="schedule-meta"><strong>반복:</strong> ${schedule.repeatType || 'NONE'}</div>

            <div class="schedule-actions">
                <button class="btn" data-action="edit" data-id="${schedule.id}">수정</button>
                <button class="btn danger" data-action="delete" data-id="${schedule.id}">삭제</button>
                <button class="btn" data-action="toggle-important" data-id="${schedule.id}" data-important="${schedule.important}">
                    중요도 변경
                </button>
            </div>
        </div>
    `).join('');
}

async function handleCreateSchedule(event) {
    event.preventDefault();

    const body = {
        title: document.getElementById('title').value.trim(),
        description: document.getElementById('description').value.trim(),
        startAt: document.getElementById('startAt').value,
        endAt: document.getElementById('endAt').value,
        important: document.getElementById('important').checked
    };

    try {
        await apiFetch('/api/schedules', {
            method: 'POST',
            body: JSON.stringify(body)
        });

        scheduleForm.reset();
        await loadInitialData();
    } catch (error) {
        alert(error.message);
        console.error(error);
    }
}

async function handleCreateRepeatSchedule(event) {
    event.preventDefault();

    const body = {
        title: document.getElementById('repeatTitle').value.trim(),
        description: document.getElementById('repeatDescription').value.trim(),
        startAt: document.getElementById('repeatStartAt').value,
        endAt: document.getElementById('repeatEndAt').value,
        important: document.getElementById('repeatImportant').checked,
        repeatType: document.getElementById('repeatType').value,
        repeatCount: Number(document.getElementById('repeatCount').value)
    };

    try {
        await apiFetch('/api/schedules/repeat', {
            method: 'POST',
            body: JSON.stringify(body)
        });

        repeatForm.reset();
        document.getElementById('repeatCount').value = 3;
        await loadInitialData();
    } catch (error) {
        alert(error.message);
        console.error(error);
    }
}

function fillEditForm(schedule) {
    document.getElementById('editScheduleId').value = schedule.id;
    document.getElementById('editTitle').value = schedule.title || '';
    document.getElementById('editDescription').value = schedule.description || '';
    document.getElementById('editStartAt').value = toInputDateTime(schedule.startAt);
    document.getElementById('editEndAt').value = toInputDateTime(schedule.endAt);
    document.getElementById('editImportant').checked = !!schedule.important;

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

async function handleEditSchedule(event) {
    event.preventDefault();

    const scheduleId = document.getElementById('editScheduleId').value;
    if (!scheduleId) {
        alert('수정할 일정을 먼저 선택해주세요.');
        return;
    }

    const body = {
        title: document.getElementById('editTitle').value.trim(),
        description: document.getElementById('editDescription').value.trim(),
        startAt: document.getElementById('editStartAt').value,
        endAt: document.getElementById('editEndAt').value,
        important: document.getElementById('editImportant').checked
    };

    try {
        await apiFetch(`/api/schedules/${scheduleId}`, {
            method: 'PUT',
            body: JSON.stringify(body)
        });

        resetEditForm();
        await loadInitialData();
    } catch (error) {
        alert(error.message);
        console.error(error);
    }
}

async function handleDeleteSchedule(scheduleId) {
    const confirmed = confirm('정말 삭제하시겠습니까?');
    if (!confirmed) return;

    try {
        await apiFetch(`/api/schedules/${scheduleId}`, {
            method: 'DELETE'
        });

        await loadInitialData();
    } catch (error) {
        alert(error.message);
        console.error(error);
    }
}

async function handleToggleImportant(scheduleId, important) {
    try {
        await apiFetch(`/api/schedules/${scheduleId}/important`, {
            method: 'PATCH',
            body: JSON.stringify({ important })
        });

        await loadInitialData();
    } catch (error) {
        alert(error.message);
        console.error(error);
    }
}

async function handleLogout() {
    try {
        await apiFetch('/api/auth/logout', {
            method: 'POST'
        });
    } catch (error) {
        console.error(error);
    } finally {
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('displayName');
        window.location.href = '/login.html';
    }
}

function resetEditForm() {
    editForm.reset();
    document.getElementById('editScheduleId').value = '';
}

function formatDateTime(value) {
    if (!value) return '-';
    return value.replace('T', ' ');
}

function toInputDateTime(value) {
    if (!value) return '';
    return value.length >= 16 ? value.slice(0, 16) : value;
}

function escapeHtml(value) {
    return String(value)
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#39;');
}