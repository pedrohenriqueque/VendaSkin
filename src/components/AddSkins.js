import { useState, useEffect } from "react";
import './items.css'

export default function SearchItems() {
  const [query, setQuery] = useState("");
  const [items, setItems] = useState([]);
  const [selectedItem, setSelectedItem] = useState(null);
  const [price, setPrice] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 24;

  useEffect(() => {
    if (query.length < 2) {
      setItems([]);
      return;
    }

    const fetchItems = async () => {
      setLoading(true);
      const token = localStorage.getItem("token");

      try {
        const response = await fetch(`http://localhost:8080/api/item/search?q=${query}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) throw new Error("Erro ao buscar itens");

        const data = await response.json();
        setItems(data);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    const delayDebounce = setTimeout(fetchItems, 500);
    return () => clearTimeout(delayDebounce);
  }, [query]);

  const handleItemClick = (item) => {
    setSelectedItem(item);
    setPrice("");
    setMessage("");
  };

  const handleAddSkin = async () => {
    if (!selectedItem || !price) {
      setMessage("Preencha o preço!");
      return;
    }

    const token = localStorage.getItem("token");

    try {
      const response = await fetch("http://localhost:8080/api/skins/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          id: selectedItem.id,
          price: parseFloat(price),
        }),
      });

      if (!response.ok) throw new Error("Erro ao adicioXnar skin");

      setMessage("Skin adicionada com sucesso!");
      setPrice("");
      setSelectedItem(null);
    } catch (error) {
      console.error(error);
      setMessage("Erro ao adicionar skin.");
    }
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = items.slice(indexOfFirstItem, indexOfLastItem);

  const nextPage = () => {
    if (currentPage < Math.ceil(items.length / itemsPerPage)) {
      setCurrentPage(currentPage + 1);
    }
  };

  const prevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6">
      <h1 className="text-2xl font-bold mb-4">Buscar Itens</h1>

      <input
        type="text"
        placeholder="Buscar itens..."
        className="w-full p-2 border rounded-md"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />

      {loading && <p className="text-gray-500 mt-2">Carregando...</p>}

      <div className="items-container mt-4">
        {currentItems.map((item) => (
          <div
            key={item.id}
            className="item-card"
            onClick={() => handleItemClick(item)}
          >
            <img
              src={item.imageUrl}
              alt={item.name}
              className="w-16 h-16 object-cover rounded-md mr-4"
            />
            <div>
              <h3 className="text-lg font-semibold">{item.name}</h3>
            </div>
          </div>
        ))}
      </div>

      <div className="pagination">
        <button onClick={prevPage} disabled={currentPage === 1}>
          Anterior
        </button>
        <span>Página {currentPage}</span>
        <button
          onClick={nextPage}
          disabled={currentPage >= Math.ceil(items.length / itemsPerPage)}
        >
          Próxima
        </button>
      </div>

      {selectedItem && (
        <div className="mt-6 p-4 border rounded-md">
          <h2 className="text-xl font-bold">{selectedItem.name}</h2>
          <img
            src={selectedItem.imageUrl}
            alt={selectedItem.name}
            className="w-32 h-32 object-cover my-2 rounded-md"
          />
          <input
            type="number"
            placeholder="Preço da skin"
            className="w-full p-2 border rounded-md mt-2"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
          />
          <button
            onClick={handleAddSkin}
            className="w-full mt-2 bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600"
          >
            Criar Skin
          </button>
          {message && <p className="mt-2 text-green-600">{message}</p>}
        </div>
      )}
    </div>
  );
}
