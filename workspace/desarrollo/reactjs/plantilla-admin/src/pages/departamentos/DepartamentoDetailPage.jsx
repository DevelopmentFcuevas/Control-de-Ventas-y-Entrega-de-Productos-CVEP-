import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from '../../services/api';
import Header from '../../components/common/Header';
import Breadcrumb from '../../components/common/Breadcrumb';
import worldGlobe from '../../assets/world-globe.png'; // usa una imagen ilustrativa como en el formulario

const DepartamentoDetailPage = () => {
    const { id } = useParams();
    const [departamento, setDepartamento] = useState(null);

    useEffect(() => {
        axios.get(`/departamentos/${id}`)
            .then(res => setDepartamento(res.data))
            .catch(err => console.error("Error al obtener departamento:", err));
    }, [id]);

    if (!departamento) {
        return <div className="text-white">Cargando...</div>;
    }

    return (
        <div className="flex-1 overflow-auto relative z-10 bg-gray-900">
            <Header title={`Detalles de ${departamento.name}`} />

            <Breadcrumb items={[
                { label: 'Departamentos', href: '/departamentos' },
                { label: `Detalles de ${departamento.name}` }
            ]} />

            <main className='max-w-7xl mx-auto py-6 px-4 lg:px-8'>
                <div className='grid grid-cols-1 lg:grid-cols-2 gap-6'>

                    {/* Columna de Detalles */}
                    <div className="bg-gray-800 p-6 rounded-2xl text-white shadow space-y-4">
                        <h2 className="text-2xl font-bold mb-4">Información General</h2>
                        <p><strong>Nombre:</strong> {departamento.name}</p>
                        <p><strong>Código ISO:</strong> {departamento.codigoIso}</p>
                        <p><strong>Capital:</strong> {departamento.capital}</p>
                        <p><strong>Region:</strong> {departamento.region.replace(/_/g, ' ')}</p>
                        <p><strong>Poblacion:</strong> {departamento.poblacion}</p>
                        <p><strong>Superficie:</strong> {departamento.superficie}</p>
                        <p><strong>País:</strong> {departamento.pais?.name || 'No especificado'}</p>
                        <p><strong>Estado:</strong> {departamento.estado}</p>
                    </div>

                    {/* Columna de Imagen */}
                    <div className="hidden lg:flex items-center justify-center">
                        {departamento.banderaUrl ? (
                            <img
                                src={departamento.banderaUrl}
                                alt={`Bandera de ${departamento.name}`}
                                className="w-3/4 max-w-sm rounded shadow-lg"
                            />
                        ) : (
                            <img
                                src={worldGlobe}
                                alt="Ilustración mundo"
                                className="w-3/4 max-w-sm opacity-80"
                            />
                        )}
                    </div>

                </div>
            </main>
        </div>
    )
}

export default DepartamentoDetailPage;