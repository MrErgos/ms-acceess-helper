document.addEventListener("DOMContentLoaded", () => {
    const editBtn = document.getElementById("editMemberButton");
    const table = document.querySelector(".member-details");
    if (!editBtn || !table) return;

    let isEditing = false;
    let savedRegistryNum = null;

    const tdRegistry = table.querySelector("tbody tr:first-child td");
    if (tdRegistry) {
        savedRegistryNum = tdRegistry.textContent.trim();
        console.log("📋 Инициализация — найден registryNum:", savedRegistryNum);
    }

    const createInput = (value, name, readOnly = false) => {
        const input = document.createElement("input");
        input.type = "text";
        input.name = name;
        input.value = value?.trim() || "";
        input.classList.add("inline-input");
        if (readOnly) {
            input.readOnly = true;
            input.classList.add("readonly");
        }
        return input;
    };

    const showToast = (message, type = "success") => {
        let toast = document.getElementById("toast");
        if (!toast) {
            toast = document.createElement("div");
            toast.id = "toast";
            document.body.appendChild(toast);
        }
        toast.textContent = message;
        toast.style.backgroundColor = type === "error" ? "#e53935" : "#4caf50";
        toast.classList.add("show");
        setTimeout(() => toast.classList.remove("show"), 3000);
    };

    editBtn.addEventListener("click", (e) => {
        e.preventDefault();

        if (!isEditing) {
            isEditing = true;
            editBtn.value = "💾 Сохранить";

            table.querySelectorAll("tbody tr").forEach((row, index) => {
                const th = row.querySelector("th");
                const td = row.querySelector("td");
                if (!th || !td) return;

                const label = th.textContent.trim();
                const rawValue = td.textContent.trim();
                const isRegistryNum = index === 0 || label.toLowerCase().includes("регистрацион");

                const fieldName = td.dataset.field || label.replace(/\s+/g, "");

                const input = createInput(rawValue, fieldName, isRegistryNum);
                td.innerHTML = "";
                td.appendChild(input);

                if (isRegistryNum) {
                    const lock = document.createElement("span");
                    lock.textContent = " 🔒";
                    lock.style.color = "#777";
                    lock.style.marginLeft = "6px";
                    td.appendChild(lock);
                }
            });

            console.log("✏️ Включен режим редактирования");
        } else {
            const registryNum = savedRegistryNum;
            if (!registryNum) {
                showToast("⚠️ Не удалось определить номер реестра.", "error");
                return;
            }

            const payload = {};
            table.querySelectorAll(".inline-input").forEach((input) => {
                if (!input.readOnly && input.name) {
                    payload[input.name] = input.value;
                }
            });

            const formBody = Object.entries(payload)
                .map(([key, val]) => encodeURIComponent(key) + "=" + encodeURIComponent(val))
                .join("&");

            console.log("🚀 Отправка данных:");
            console.log("📦 Payload объект:", payload);
            console.log("🔗 URL:", `/members/${encodeURIComponent(registryNum)}/edit`);

            fetch(`/members/${encodeURIComponent(registryNum)}/edit`, {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: formBody,
            })
                .then(async (res) => {
                    const html = await res.text();
                    console.log("📄 Получен HTML:", html.substring(0, 400));

                    // временно создаём "виртуальный" документ
                    const tempDoc = new DOMParser().parseFromString(html, "text/html");
                    const successFlag = tempDoc.querySelector("[data-save-success]");
                    const hasSuccess = successFlag?.getAttribute("data-save-success") === "true";

                    document.body.innerHTML = html;

                    if (hasSuccess) {
                        showToast("✅ Изменения сохранены!");
                        setTimeout(() => location.reload(), 1200);
                    } else {
                        showToast("⚠️ Есть ошибки при сохранении (проверьте поля).", "error");
                    }
                })
                .catch((err) => {
                    console.error("❌ Ошибка при fetch:", err);
                    showToast("❌ Не удалось отправить данные.", "error");
                });
        }
    });
});