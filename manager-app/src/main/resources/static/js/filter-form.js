document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".filter-form");
    const input = form?.querySelector('input[type="text"]');
    const button = form?.querySelector('button[type="submit"]');

    if (!input || !button) return;

    const clearBtn = document.createElement("span");
    clearBtn.classList.add("filter-clear");
    clearBtn.textContent = "×";
    form.appendChild(clearBtn);

    const updateClearState = () => {
        const hasText = input.value.trim().length > 0;
        clearBtn.classList.toggle("visible", hasText);
    };

    input.addEventListener("input", updateClearState);

    clearBtn.addEventListener("click", () => {
        input.value = "";
        updateClearState();
        input.focus();
    });

    updateClearState();
});
