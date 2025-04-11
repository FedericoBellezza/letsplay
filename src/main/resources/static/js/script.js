// modal
function openModal(name, id, type) {
  document.getElementById("modalOverlay").classList.remove("hidden");
  document.getElementById("title").textContent = name;
  document.getElementById("deleteForm").action = `/${type}/delete/${id}`;
  if (type == "categories") {
    document.getElementById(
      "alertMessage"
    ).textContent = `Se elimina questa categoria cancellerai anche tutti gli eventi collegati ad essa`;
  }
}
function closeModal() {
  document.getElementById("modalOverlay").classList.add("hidden");
}

// filters for advanced search
function openFilters() {
  document.getElementById("searchForm").classList.remove("hidden");
  document.getElementById("filtersButton").classList.add("hidden");
}
function closeFilters() {
  document.getElementById("searchForm").classList.add("hidden");
  document.getElementById("filtersButton").classList.remove("hidden");
}

// dropdown menu
function openDropdownMenu() {
  document.getElementById("dropdownMenu").classList.remove("hidden");
  document.getElementById("openDropdownMenuButton").classList.add("hidden");
  document.getElementById("closeDropdownMenuButton").classList.remove("hidden");
}
function closeDropdownMenu() {
  document.getElementById("dropdownMenu").classList.add("hidden");
  document.getElementById("openDropdownMenuButton").classList.remove("hidden");
  document.getElementById("closeDropdownMenuButton").classList.add("hidden");
}

// table sorting
function sortBy(id) {
  let table = document.getElementById("eventsTable");
  let rows = Array.from(table.rows).slice(1);
  let headerCell = table.rows[0].cells[id];
  let isAscending = headerCell.getAttribute("data-sort-direction") === "asc";

  rows.sort((rowA, rowB) => {
    let cellA = rowA.cells[id].textContent.trim();
    let cellB = rowB.cells[id].textContent.trim();

    let numA = parseFloat(cellA);
    let numB = parseFloat(cellB);
    let isNumber = !isNaN(numA) && !isNaN(numB);

    let dateA = new Date(cellA);
    let dateB = new Date(cellB);
    let isValidDate =
      !isNaN(dateA) &&
      !isNaN(dateB) &&
      /^\d{4}-\d{2}-\d{2}$/.test(cellA) &&
      /^\d{4}-\d{2}-\d{2}$/.test(cellB);

    if (isValidDate) {
      return isAscending ? dateA - dateB : dateB - dateA;
    } else if (isNumber) {
      return isAscending ? numA - numB : numB - numA;
    } else {
      return isAscending
        ? cellA.localeCompare(cellB)
        : cellB.localeCompare(cellA);
    }
  });

  rows.forEach((row) => table.appendChild(row));

  headerCell.setAttribute("data-sort-direction", isAscending ? "desc" : "asc");
}

// display event create or edit form
function displayEndDate() {
  document.getElementById("endDateInput").classList.remove("hidden");
  document.getElementById("endDateButton").classList.add("hidden");
}

// display registration dates
function displayRegistrationDate() {
  document.getElementById("registrationDateButton").classList.add("hidden");
  document
    .getElementById("registrationDateContainer")
    .classList.remove("hidden");
}

// category form
function addImageUrl() {
  const container = document.getElementById("imageUrls");
  const nextIndex = document.querySelectorAll(
    '#imageUrls input[type="text"]'
  ).length;

  const div = document.createElement("div");
  div.className = "image-url flex justify-between items-baseline gap-3";
  div.innerHTML = `
    <div class="flex flex-col mt-5">
      <input class="rounded-lg shadow-lg border-gray-300 border ps-2 py-1" 
            type="text" 
            name="imageUrls[${nextIndex}]" 
            placeholder="Inserisci URL immagine">
    </div>
    <button class="w-full border border-red-300 px-3 py-1 rounded-2xl text-gray-700 hover:bg-red-100 transition cursor-pointer mt-5 shadow-lg" 
            type="button" 
            onclick="removeImageUrl(this)">
      Rimuovi
    </button>
  `;
  container.appendChild(div);
}
function removeImageUrl(button) {
  const container = document.getElementById("imageUrls");
  button.parentElement.remove();
  updateInputIndexes();

  // Funzione per riaggiornare tutti gli indici
  function updateInputIndexes() {
    const inputs = document.querySelectorAll('#imageUrls input[type="text"]');
    inputs.forEach((input, index) => {
      input.name = `imageUrls[${index}]`;
    });
  }
}
