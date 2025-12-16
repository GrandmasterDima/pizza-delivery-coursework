// Функція обробки реєстрації
// Викликається при натисканні кнопки зареєструватися
async function handleRegister(event) {
    //Зупиняємо стандартну поведінку браузера.
    //За замовчуванням форма хоче перезавантажити сторінку і відправити дані в URL.
    //Ми це блокуємо, бо хочемо відправити дані через JavaScript (fetch).
    event.preventDefault();

    //Збираємо дані з полів введення
    //Ми створюємо об'єкт userData, який точно відповідає полям у Java-класі User.
    const userData = {
        login: document.getElementById('login').value,     // Те, що ввів користувач
        password: document.getElementById('password').value,
        name: document.getElementById('name').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        role: 'client' // Жорстко задаємо роль. Адміна через цю форму створити не можна
    };

    try {
        //Відправляємо дані на сервер Java Controller
        //await змушує код чекати, поки сервер не відповість
        const response = await fetch('/api/users/register', {
            method: 'POST', //Використовуємо метод POST для створення нових даних
            headers: {
                'Content-Type': 'application/json' // Кажемо серверу,що надсилаємо йому json
            },
            body: JSON.stringify(userData) // Перетворюємо JS-об'єкт у текстовий рядок JSON
        });

        //Перевіряємо відповідь сервера
        //response.ok = true, якщо статус 200 все добре
        if (response.ok) {
            alert('Реєстрація успішна! Тепер увійдіть.');
            // Перенаправляємо користувача на сторінку входу
            window.location.href = '/login';
        } else {
            // Якщо сервер повернув помилку наприклад, 400 - такий логін вже є
            alert('Помилка реєстрації. Можливо, такий логін вже зайнятий.');
        }
    } catch (e) {
        // Цей блок спрацює, якщо сервер вимкнений або зник інтернет
        console.error(e);
        alert('Помилка сервера.');
    }
}