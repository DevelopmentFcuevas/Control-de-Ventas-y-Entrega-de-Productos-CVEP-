import axios from "axios";

// Configuración base de Axios para todas las peticiones HTTP.
// Esto centraliza el uso de la API para facilitar mantenimiento y cambios de URL base.
const api = axios.create({
    baseURL: 'http://localhost:8080/api', // Dirección base de tu backend
});


// Obtener cantidad de países por estado
//export const getPaisesPorEstado = (estado) =>
//    api.get(`/paises/count/estado`, {
//        params: { estado }
//    });
export const getPaisesPorEstado = (estado) =>
    api.get(`/paises/count/estado/${estado}`);

// Obtener cantidad de países creados por fecha
//export const getPaisesPorFecha = (fecha) =>
//    api.get(`/paises/count/fecha`, {
//        params: { fecha }
//    });
export const getPaisesPorFecha = (fecha) =>
    api.get(`/paises/count/fecha/${fecha}`);


/* 
🧠 Sugerencias para crecer con este componente:
------------------------------------------------

* En api.js, podés sumar interceptores de Axios para agregar tokens, manejar errores globales, etc.
*/

export default api;