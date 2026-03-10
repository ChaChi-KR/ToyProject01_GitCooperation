const loginForm = document.getElementById('loginForm');
const errorMessage = document.getElementById('errorMessage');

loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    errorMessage.textContent = '';

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin',
            body: JSON.stringify({
                username,
                password
            })
        });

        const data = await response.json();

        if (!response.ok || !data.success) {
            errorMessage.textContent = data.message || '로그인에 실패했습니다.';
            return;
        }

        sessionStorage.setItem('username', data.username || '');
        sessionStorage.setItem('displayName', data.displayName || '');

        window.location.href = '/schedule.html';
    } catch (error) {
        errorMessage.textContent = '서버 요청 중 오류가 발생했습니다.';
        console.error(error);
    }
});