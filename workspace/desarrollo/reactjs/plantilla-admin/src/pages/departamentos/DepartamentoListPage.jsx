// 📦 Librerías externas
import React, { useEffect, useState } from 'react';
import { motion } from 'framer-motion';                                                     // Librería para animaciones
import dayjs from 'dayjs';                                                                  // Para manejar fechas fácilmente
import { Link } from 'react-router-dom';                                                    // Navegación interna con React Router
// 📁 Íconos u otros recursos externos
import { Flag, FlagOff, Goal, LandPlot } from 'lucide-react';                               // Íconos para estadísticas
// 🔧 Servicios (API, helpers, utilidades)
import { getDepartamentosPorEstado, getDepartamentosPorFecha } from '../../services/api';   // Cliente Axios centralizado
// 🧩 Componentes comunes
import Header from '../../components/common/Header';                                        // Título de la sección
import StatCard from '../../components/common/StatCard';                                    // Tarjetas de estadísticas
//Componentes específicos
import DepartamentoTable from '../../components/departamentos/DepartamentoTable';           // Tabla de datos (ahora de departamentos)


/**
 * Página Crear Departamento que muestra el formulario de departamentos junto con estadísticas rápidas.
 * Se encarga de guardar datos de departamento hacia la API.
 */
const DepartamentoListPage = () => {
    
    const [stats, setStats] = useState({
        totalDepartamentos: 0,
        newDepartamentosToday: 0,
        activeDepartamentos: 0,
        inactiveDepartamentos: 0,
    });

    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const [activosRes, inactivosRes, hoyRes] = await Promise.all([
                    getDepartamentosPorEstado("ACTIVO"),
                    getDepartamentosPorEstado("INACTIVO"),
                    getDepartamentosPorFecha(dayjs().format('YYYY-MM-DD')),
                ]);

                if (typeof activosRes.data !== 'number' || typeof inactivosRes.data !== 'number') {
                    console.error("Error en la respuesta del servidor");
                    throw new Error("La respuesta del servidor no es válida.");
                }

                const total = activosRes.data + inactivosRes.data;

                setStats({
                    totalDepartamentos: total,
                    newDepartamentosToday: hoyRes.data,
                    activeDepartamentos: activosRes.data,
                    inactiveDepartamentos: inactivosRes.data,
                });
            } catch (error) {
                console.error("Error al obtener estadísticas:", error);
                setError("No se pudieron cargar las estadísticas. Intente más tarde.");
            }
        };

        fetchStats();
    }, []);

    return (
        <div className='flex-1 overflow-auto relative z-10'>
            
            {/* Header superior de la página */}
            <Header title='Departamentos' />

            {error && (
                <div className="bg-red-100 text-red-800 px-4 py-3 rounded mb-4">
                    {error}
                </div>
            )}

            {/* Contenido principal */}
            <main className=' max-w-7xl mx-auto py-6 px-4 lg:px-8 '>
                
                {/* Tarjetas con estadísticas rápidas */}
                <motion.div
                    className='grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4 mb-8'
                    initial={{ opacity: 0, y: 200 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 1 }}
                >
                    <StatCard name="Total de Departamentos" icon={Flag} value={stats.totalDepartamentos.toLocaleString()} color='#6366F1' />
                    <StatCard name="Nuevos Departamentos Agregados(hoy)" icon={LandPlot} value={stats.newDepartamentosToday} color='#10B981' />
                    <StatCard name="Departamentos Activos" icon={Goal} value={stats.activeDepartamentos.toLocaleString()} color='#F59E0B' />
                    <StatCard name="Departamentos Inactivos" icon={FlagOff} value={stats.inactiveDepartamentos} color='#EF4444' />
                </motion.div>

                <div className="flex justify-end mb-4">
                    <Link
                        to="/departamentos/nuevo"
                        className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                    >
                        + Agregar Departamento
                    </Link>
                </div>

                {/* Tabla con los datos detallados */}
                <DepartamentoTable />
            </main>

        </div>
    )

}

export default DepartamentoListPage;