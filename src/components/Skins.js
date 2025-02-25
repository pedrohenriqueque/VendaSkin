import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './skins.css'; // Importando o CSS

const Skins = () => {
    const [skins, setSkins] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 24; // Número de itens por página

    useEffect(() => {
        const fetchSkins = async () => {
            const token = localStorage.getItem("token"); // Certifique-se de que o token está salvo com essa chave
            try {
                const response = await axios.get('http://localhost:8080/api/skins/disponivel', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setSkins(response.data);
            } catch (error) {
                setError('Erro ao buscar skins');
                console.error('Erro ao buscar skins:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchSkins();
    }, []);

    // Calcular os itens a serem exibidos na página atual
    const indexOfLastSkin = currentPage * itemsPerPage;
    const indexOfFirstSkin = indexOfLastSkin - itemsPerPage;
    const currentSkins = skins.slice(indexOfFirstSkin, indexOfLastSkin);

    // Funções para navegação entre páginas
    const nextPage = () => {
        if (currentPage < Math.ceil(skins.length / itemsPerPage)) {
            setCurrentPage(currentPage + 1);
        }
    };

    const prevPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    // Função para realizar a compra
    const handleBuy = async (skinId) => {
        const sellerId = 1; // ID do vendedor (exemplo)
        const token = localStorage.getItem("token"); // Obtenha o token novamente
        console.log("Token JWT:", token);

        try {
            const response = await axios.post(`http://localhost:8080/api/transaction`, null, {
                headers: {
                    Authorization: `Bearer ${token}` // Adiciona o token ao cabeçalho Authorization
                },
                params: {
                    sellerId,
                    skinId
                }
            });
            console.log('Compra realizada com sucesso:', response.data);
            alert('Compra realizada com sucesso!'); // Mensagem de sucesso
        } catch (error) {
            console.error('Erro ao realizar a compra:', error);
            alert('Erro ao realizar a compra. Tente novamente.'); // Mensagem de erro
        }
    };

    if (loading) {
        return <div>Carregando...</div>;
    }

    if (error) {
        return <div style={{ color: 'red' }}>{error}</div>;
    }

    return (
        <div>
            <h1>Skins Disponíveis</h1>
            <div className="skins-container">
                {currentSkins.map((skin) => (
                    <div key={skin.id} className="skin-card">
                        <h2>{skin.name}</h2>
                        <img src={skin.imageUrl} alt={skin.name} />
                        {skin.price && <p>Preço: {skin.price}</p>}
                        {skin.rarity && <p>Raridade: {skin.rarity}</p>}
                        <button onClick={() => handleBuy(skin.id)}>Comprar</button> {/* Botão de compra */}
                    </div>
                ))}
            </div>
            <div className="pagination">
                <button onClick={prevPage} disabled={currentPage === 1}>
                    Anterior
                </button>
                <span>Página {currentPage}</span>
                <button onClick={nextPage} disabled={currentPage >= Math.ceil(skins.length / itemsPerPage)}>
                    Próxima
                </button>
            </div>
        </div>
    );
};

export default Skins;