import { createContext, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem("token") || null);

    const login = async (email, password) => {
        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password }),
            });

            if (!response.ok) {
                throw new Error("Erro ao autenticar");
            }

            const data = await response.json();
            localStorage.setItem("token", data.token);
            setToken(data.token);
            return { success: true, token: data.token };  // Retorna o token
        } catch (error) {
            console.error("Erro no login:", error);
            const storedToken = localStorage.getItem("token");
            return { success: false, token: storedToken };  // Retorna o token ou null
        }
    };

    const logout = () => {
        localStorage.removeItem("token");
        setToken(null);
    };

    return (
        <AuthContext.Provider value={{ token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
