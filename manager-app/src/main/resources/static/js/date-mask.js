document.addEventListener('DOMContentLoaded', function () {
    // Список ID всех полей ввода, которые должны обрабатываться как LocalDate (dd.MM.yyyy)
    const dateInputIds = [
        'dateAddedToRegistry',
        'dateOfBirth',
        'dateJoiningRp',
        'dateExclusionFromRp',
        'assessmentWorkExperience',
        'certificateDateCriminalRecord'
        // Добавьте сюда любые другие поля LocalDate, если они появятся
    ];

    /**
     * Основная логика форматирования даты (dd.MM.yyyy) при вводе
     * @param {Event} e - Событие ввода
     */
    function handleDateInput(e) {
        const input = e.target;
        let value = input.value.replace(/\D/g, ''); // убираем всё кроме цифр
        if (value.length > 8) value = value.slice(0, 8);

        // Вставляем точки по шаблону dd.MM.yyyy
        if (value.length > 4) {
            value = value.replace(/(\d{2})(\d{2})(\d{0,4})/, '$1.$2.$3');
        } else if (value.length > 2) {
            value = value.replace(/(\d{2})(\d{0,2})/, '$1.$2');
        }

        const oldValue = input.value;
        const oldPos = input.selectionStart;
        input.value = value;

        // Аккуратно возвращаем курсор, чтобы не прыгал на точки при автоматическом добавлении
        let newPos = oldPos;
        // Если новая длина больше старой (значит, добавилась точка) и курсор был перед точкой
        if (value.length > oldValue.length && value.charAt(oldPos - 1) === '.') {
            newPos = oldPos + 1;
        }
        input.setSelectionRange(newPos, newPos);
    }

    /**
     * Логика для корректного удаления символа Backspace (для обхода точек)
     * @param {Event} e - Событие нажатия клавиши
     */
    function handleDateKeydown(e) {
        const input = e.target;
        const pos = input.selectionStart;
        // при удалении точки курсор перескакивает влево
        if (e.key === 'Backspace' && pos && input.value[pos - 1] === '.') {
            e.preventDefault();
            const before = input.value.slice(0, pos - 1);
            const after = input.value.slice(pos);
            input.value = before + after;
            input.setSelectionRange(pos - 1, pos - 1);
        }
    }

    // Применяем обработчики ко всем нужным полям
    dateInputIds.forEach(id => {
        const input = document.getElementById(id);
        if (input) {
            input.addEventListener('input', handleDateInput);
            input.addEventListener('keydown', handleDateKeydown);
        } else {
            // Опционально: можно вывести в консоль, если какое-то поле не найдено
            console.warn(`Элемент с ID '${id}' не найден на странице.`);
        }
    });
});