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
