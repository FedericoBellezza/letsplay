function openModal(name, id, type) {
  document.getElementById("modalOverlay").classList.remove("hidden");
  document.getElementById("title").textContent = name;
  document.getElementById("deleteForm").action = `/${type}/delete/${id}`;

  if (type == "categories") {
    document.getElementById(
      "alertMessage"
    ).textContent = `Se elimina questa categoria cancellerai anche tutti gli eventi di essa`;
  }
}

function closeModal() {
  document.getElementById("modalOverlay").classList.add("hidden");
}
