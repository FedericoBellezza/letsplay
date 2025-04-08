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

// sort event table
function sortBy(id) {
  let table = document.getElementById("eventsTable");
  let rows = Array.from(table.rows).slice(1);
  let isAscending =
    table.rows[0].cells[id].getAttribute("data-sort-direction") === "asc";

  rows.sort((rowA, rowB) => {
    let cellA = rowA.cells[id].textContent.trim();
    let cellB = rowB.cells[id].textContent.trim();

    if (cellA < cellB) return isAscending ? -1 : 1;
    if (cellA > cellB) return isAscending ? 1 : -1;
    return 0;
  });

  rows.forEach((row) => table.appendChild(row));
  table.rows[0].cells[id].setAttribute(
    "data-sort-direction",
    isAscending ? "desc" : "asc"
  );
}
