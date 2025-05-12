import React, { useEffect, useState } from 'react';
import Header from '../components/common/Header'; // Título de la sección
import { motion } from 'framer-motion'; // Librería para animaciones
import { Flag, FlagOff, LandPlot, Goal } from 'lucide-react'; // Íconos para estadísticas
import StatCard from '../components/common/StatCard'; // Tarjetas de estadísticas
import PaisesTable from '../components/users/PaisesTable'; // Tabla de datos (ahora de países)
import { getPaisesPorEstado, getPaisesPorFecha } from '../services/api'; // Cliente Axios centralizado
import dayjs from 'dayjs'; // Para manejar fechas fácilmente
//import toast from 'react-hot-toast';
import { Link } from 'react-router-dom';

const PaisesPage = () => {
    
    const [stats, setStats] = useState({
        totalPaises: 0,
        newPaisesToday: 0,
        activePaises: 0,
        inactivePaises: 0,
    });

    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const [activosRes, inactivosRes, hoyRes] = await Promise.all([
                    getPaisesPorEstado("ACTIVO"),
                    getPaisesPorEstado("INACTIVO"),
                    getPaisesPorFecha(dayjs().format('YYYY-MM-DD')),
                ]);

                if (typeof activosRes.data !== 'number' || typeof inactivosRes.data !== 'number') {
                    throw new Error("La respuesta del servidor no es válida.");
                }

                const total = activosRes.data + inactivosRes.data;
                console.log("total: " + total + " activosRes: " + activosRes.data + " inactivosRes: " + inactivosRes.data + " hoyRes: " + hoyRes.data);

                setStats({
                    totalPaises: total,
                    newPaisesToday: hoyRes.data,
                    activePaises: activosRes.data,
                    inactivePaises: inactivosRes.data,
                });
            } catch (error) {
                console.error("Error al obtener estadísticas:", error);
                setError("No se pudieron cargar las estadísticas. Intente más tarde.");
                //toast.error("No se pudieron cargar las estadísticas. Intenta más tarde.");
            }
        };

        fetchStats();
    }, []);

    return (
        <div className='flex-1 overflow-auto relative z-10'>
            
            {/* Header superior de la página */}
            <Header title='Países' />

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
                    <StatCard name="Total de Países" icon={Flag} value={stats.totalPaises.toLocaleString()} color='#6366F1' />
                    <StatCard name="Nuevos Países Agregados(hoy)" icon={LandPlot} value={stats.newPaisesToday} color='#10B981' />
                    <StatCard name="Países Activos" icon={Goal} value={stats.activePaises.toLocaleString()} color='#F59E0B' />
                    <StatCard name="Países Inactivos" icon={FlagOff} value={stats.inactivePaises} color='#EF4444' /> 
                    
                </motion.div>

                {/* Agrega este bloque justo encima del listado o dentro de un div que alinee a la derecha */}
                <div className="flex justify-end mb-4">
                    <Link
                        to="/paises/nuevo"
                        className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                    >
                        + Agregar País
                    </Link>
                </div>

                {/* Tabla con los datos detallados */}
                <PaisesTable />
            </main>

        </div>
    )
}

export default PaisesPage;