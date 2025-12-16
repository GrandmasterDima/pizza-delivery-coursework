// Функція обробки входу
async function handleLogin(event) {
    //Знову блокуємо перезавантаження сторінки
    event.preventDefault();
    //Збираємо тільки логін і пароль
    const loginData = {
        login: document.getElementById('login').value,
        password: document.getElementById('password').value
    };

    try {
        //Стукаємо до сервера, щоб перевірити чи є такий користувач
        const response = await fetch('/api/users/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(loginData)
        });

        //Аналізуємо відповідь
        if (response.ok) {
            //Якщо сервер відповів ок і надіслав нам дані користувача
            //response.json() розпаковує ці дані з відповіді
            const user = await response.json();

            //Ключовий момент авторизації
            //Ми зберігаємо об'єкт user у пам'ять браузера
            //Тепер, навіть якщо користувач закриє вкладку, браузер пам'ятатиме, хто він.
            // Саме звідси client.js і cart.js будуть дізнаватися ім'я та ID клієнта.
            localStorage.setItem('currentUser', JSON.stringify(user));

            alert('Ласкаво просимо, ' + user.name + '!');
            // Перекидаємо на головну сторінку, де вже зміниться шапка сайту
            window.location.href = '/';
        } else {
            // Якщо сервер сказав 401 значить неправильні дані
            alert('Невірний логін або пароль');
        }
    } catch (e) {
        console.error(e);
        alert('Помилка сервера');
    }
}