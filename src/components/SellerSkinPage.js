import { useState, useEffect } from "react";
import { useParams } from "react-router-dom"; // Para pegar o sellerId da URL

export default function SellerSkinsPage() {
  const { sellerId } = useParams();
  const [skins, setSkins] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSkins = async () => {
      setLoading(true);
      const token = localStorage.getItem("token"); // Obtendo o token do localStorage

      try {
        const response = await fetch(`http://localhost:8080/api/skins/seller/${sellerId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) throw new Error("Erro ao buscar skins");
        const data = await response.json();
        setSkins(data);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    fetchSkins();
  }, [sellerId]);

  if (loading) return <p>Carregando skins...</p>;

  return (
    <div className="max-w-4xl mx-auto p-6">
      <h1 className="text-2xl font-bold mb-4">Skins do Vendedor {sellerId}</h1>
      <div className="grid grid-cols-3 gap-4">
        {skins.map((skin) => (
          <div key={skin.id} className="border p-4 rounded-md">
            <img src={skin.imageUrl} alt={skin.name} className="w-full h-32 object-cover rounded-md" />
            <h3 className="mt-2 text-lg font-semibold">{skin.name}</h3>
            <p className="text-gray-600">Preço: ${skin.price.toFixed(2)}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
