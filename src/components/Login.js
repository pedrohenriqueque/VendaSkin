import { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const { login, logout } = useContext(AuthContext);
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState("");
    const [token, setToken] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token")); // Verifica se o token existe

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage("");  // Limpar mensagens de erro anteriores
        setToken(null);       // Limpar token antes de nova tentativa

        const { success, token: responseToken } = await login(email, password);

        // Se o login foi bem-sucedido, redireciona
        if (success) {
            localStorage.setItem("token", responseToken); // Armazena o token no localStorage
            setIsLoggedIn(true); // Atualiza o estado de login
            navigate("/skins");
        } else {
            // Se houver erro, exibe o token (se houver)
            setErrorMessage("Falha na autenticação. Verifique suas credenciais.");
            if (responseToken) {
                setToken(responseToken);  // Exibe o token, mesmo com erro
            }
        }
    };

    const handleLogout = () => {
        logout(); // Chama a função de logout do contexto
        localStorage.removeItem("token"); // Remove o token do localStorage
        setIsLoggedIn(false); // Atualiza o estado de login
        navigate("/login"); // Redireciona para a página de login
    };

    return (
        <div>
            <h2>{isLoggedIn ? "Bem-vindo de volta!" : "Login"}</h2>
            {isLoggedIn ? (
                <div>
                    <button onClick={handleLogout}>Logout</button>
                </div>
            ) : (
                <form onSubmit={handleSubmit}>
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="Senha"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit">Entrar</button>
                </form>
            )}

            {errorMessage && <div style={{ color: "red" }}>{errorMessage}</div>}

            {/* Exibe o token, mesmo em caso de erro */}
            {token && (
                <div style={{ marginTop: "20px" }}>
                    <strong>Token:</strong> {token}
                </div>
            )}
        </div>
    );
};

export default Login;