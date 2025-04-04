function openModal(name, id, type) {
  document.getElementById("modalOverlay").classList.remove("hidden");
  document.getElementById("title").textContent = name;
  document.getElementById("deleteForm").action = `/${type}/delete/${id}`;
}

function closeModal() {
  document.getElementById("modalOverlay").classList.add("hidden");
}
