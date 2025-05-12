import React from 'react';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from '../services/api';
import Header from '../components/common/Header';
import Breadcrumb from '../components/common/Breadcrumb';
import worldGlobe from '../assets/world-globe.png'; // usa una imagen ilustrativa como en el formulario

const PaisDetailPage = () => {

    const { id } = useParams();
    const [pais, setPais] = useState(null);

    useEffect(() => {
        axios.get(`/paises/${id}`)
            .then(res => setPais(res.data))
            .catch(err => console.error("Error al obtener país:", err));
    }, [id]);

    if (!pais) {
        return <div className="text-white">Cargando...</div>;
    }

    return (
        <div className="flex-1 overflow-auto relative z-10 bg-gray-900">
            <Header title={`Detalles de ${pais.name}`} />

            <Breadcrumb items={[
                { label: 'Países', href: '/paises' },
                { label: `Detalles de ${pais.name}` }
            ]} />

            <main className='max-w-7xl mx-auto py-6 px-4 lg:px-8'>
                <div className='grid grid-cols-1 lg:grid-cols-2 gap-6'>

                    {/* Columna de Detalles */}
                    <div className="bg-gray-800 p-6 rounded-2xl text-white shadow space-y-4">
                        <h2 className="text-2xl font-bold mb-4">Información General</h2>
                        <p><strong>Nombre:</strong> {pais.name}</p>
                        <p><strong>Código ISO2:</strong> {pais.codigoIso2}</p>
                        <p><strong>Código ISO3:</strong> {pais.codigoIso3}</p>
                        <p><strong>Capital:</strong> {pais.capital}</p>
                        {/* <p><strong>Continente:</strong> {pais.continente}</p> */}
                        <p><strong>Continente:</strong> {pais.continente.replace(/_/g, ' ')}</p>
                        <p><strong>Idioma:</strong> {pais.idioma}</p>
                        <p><strong>Moneda:</strong> {pais.moneda}</p>
                        <p><strong>Dominio TLD:</strong> {pais.dominioTld}</p>
                        <p><strong>Estado:</strong> {pais.estado}</p>
                        {/* Descomenta si están disponibles */}
                        {/* <p><strong>Población:</strong> {pais.poblacion?.toLocaleString()}</p> */}
                        {/* <p><strong>Área:</strong> {pais.area?.toLocaleString()} km²</p> */}
                    </div>

                    {/* Columna de Imagen */}
                    <div className="hidden lg:flex items-center justify-center">
                        {pais.banderaUrl ? (
                            <img
                                src={pais.banderaUrl}
                                alt={`Bandera de ${pais.name}`}
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

export default PaisDetailPage;