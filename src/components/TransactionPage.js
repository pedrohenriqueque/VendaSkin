import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

export default function TransactionsPage() {
  const { id } = useParams(); // ID do vendedor ou comprador
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchTransactions = async () => {
      const endpoint = id
        ? `http://localhost:8080/api/transaction/${id}` 
        : `http://localhost:8080/api/transaction`; 

      try {
        const response = await fetch(endpoint, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          throw new Error("Erro ao buscar transações");
        }

        const data = await response.json();
        setTransactions(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    if (token) {
      fetchTransactions();
    } else {
      setError("Token inválido ou ausente.");
      setLoading(false);
    }
  }, [id, token]); // Incluindo 'token' como dependência

  if (loading) return <p>Carregando transações...</p>;

  if (error) return <p className="text-red-500">Erro: {error}</p>;

  if (transactions.length === 0) {
    return <p>Nenhuma transação encontrada.</p>;
  }

  return (
    <div className="max-w-4xl mx-auto p-6">
      <h1 className="text-2xl font-bold mb-4">
        {id ? `Transações do ID: ${id}` : "Todas as Transações"}
      </h1>
      <div className="grid grid-cols-1 gap-4">
        {transactions.map((transaction) => (
          <div key={transaction.id} className="border p-4 rounded-md">
            <p>Comprador: {transaction.buyerName}</p>
            <p>Vendedor: {transaction.sellerName}</p>
            <p>Item: {transaction.itemName}</p>
            <p>Preço: ${transaction.price.toFixed(2)}</p>
            <p>Data: {new Date(transaction.date).toLocaleDateString()}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
