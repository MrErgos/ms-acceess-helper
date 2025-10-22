document.addEventListener('DOMContentLoaded', function () {
    const input = document.getElementById('dateAddedToRegistry');

    input.addEventListener('input', function (e) {
        let value = input.value.replace(/\D/g, ''); // убираем всё кроме цифр
        if (value.length > 8) value = value.slice(0, 8);

        // вставляем точки по шаблону dd.MM.yyyy
        if (value.length > 4) {
            value = value.replace(/(\d{2})(\d{2})(\d{0,4})/, '$1.$2.$3');
        } else if (value.length > 2) {
            value = value.replace(/(\d{2})(\d{0,2})/, '$1.$2');
        }

        const oldValue = input.value;
        const oldPos = input.selectionStart;
        input.value = value;

        // аккуратно возвращаем курсор, чтобы не прыгал на точки
        let newPos = oldPos;
        if (value.length > oldValue.length && value.charAt(oldPos - 1) === '.') {
            newPos = oldPos + 1;
        }
        input.setSelectionRange(newPos, newPos);
    });

    input.addEventListener('keydown', function (e) {
        // при удалении точки курсор перескакивает влево
        const pos = input.selectionStart;
        if (e.key === 'Backspace' && pos && input.value[pos - 1] === '.') {
            e.preventDefault();
            const before = input.value.slice(0, pos - 1);
            const after = input.value.slice(pos);
            input.value = before + after;
            input.setSelectionRange(pos - 1, pos - 1);
        }
    });
});