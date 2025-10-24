document.addEventListener('DOMContentLoaded', function () {
    const dateInputIds = [
        'dateAddedToRegistry',
        'dateOfBirth',
        'dateJoiningRp',
        'dateExclusionFromRp',
        'assessmentWorkExperience',
        'certificateDateCriminalRecord'
    ];

    /**
     * The basic logic of date formatting (dd.MM.yyyy) when entering
     * @param {Event} e - Input event
     */
    function handleDateInput(e) {
        const input = e.target;
        let value = input.value.replace(/\D/g, '');
        if (value.length > 8) value = value.slice(0, 8);

        if (value.length > 4) {
            value = value.replace(/(\d{2})(\d{2})(\d{0,4})/, '$1.$2.$3');
        } else if (value.length > 2) {
            value = value.replace(/(\d{2})(\d{0,2})/, '$1.$2');
        }

        const oldValue = input.value;
        const oldPos = input.selectionStart;
        input.value = value;

        let newPos = oldPos;
        if (value.length > oldValue.length && value.charAt(oldPos - 1) === '.') {
            newPos = oldPos + 1;
        }
        input.setSelectionRange(newPos, newPos);
    }

    /**
     * Logic for correctly deleting the Backspace character (to bypass periods)
     * @param {Event} e - Key press event
     */
    function handleDateKeydown(e) {
        const input = e.target;
        const pos = input.selectionStart;
        if (e.key === 'Backspace' && pos && input.value[pos - 1] === '.') {
            e.preventDefault();
            const before = input.value.slice(0, pos - 1);
            const after = input.value.slice(pos);
            input.value = before + after;
            input.setSelectionRange(pos - 1, pos - 1);
        }
    }

    dateInputIds.forEach(id => {
        const input = document.getElementById(id);
        if (input) {
            input.addEventListener('input', handleDateInput);
            input.addEventListener('keydown', handleDateKeydown);
        } else {
            console.warn(`Элемент с ID '${id}' не найден на странице.`);
        }
    });
});