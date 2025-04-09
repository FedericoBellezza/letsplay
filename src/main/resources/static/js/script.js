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

    // Tenta di riconoscere numeri
    let numA = parseFloat(cellA);
    let numB = parseFloat(cellB);
    let isNumber = !isNaN(numA) && !isNaN(numB);

    // Tenta di riconoscere date ISO (YYYY-MM-DD)
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

  // Riappende le righe ordinate
  rows.forEach((row) => table.appendChild(row));

  // Aggiorna direzione sort
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
