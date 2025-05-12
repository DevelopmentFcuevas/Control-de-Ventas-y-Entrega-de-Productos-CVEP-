import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from '../services/api';
import { motion } from 'framer-motion';
import Header from '../components/common/Header';
import Breadcrumb from '../components/common/Breadcrumb';
import worldGlobe from '../assets/world-globe.png';
import toast from 'react-hot-toast';

const CONTINENTES = [
    'ASIA', 'AFRICA', 'AMERICA_DEL_NORTE', 'AMERICA_DEL_SUR',
    'ANTARTIDA', 'EUROPA', 'OCEANIA', 'SIN_ESPECIFICAR',
];

const PaisEditPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: '',
        codigoIso2: '',
        codigoIso3: '',
        capital: '',
        poblacion: '',
        area: '',
        idioma: '',
        moneda: '',
        dominioTld: '',
        husoHorario: '',
        continente: 'SIN_ESPECIFICAR',
    });
    const [errors, setErrors] = useState({});

    useEffect(() => {
        axios.get(`/paises/${id}`)
            .then(res => {
                const sanitized = Object.fromEntries(
                    Object.entries(res.data).map(([key, value]) => [key, value ?? ''])
                );
                setFormData(sanitized);
            })
            .catch(err => {
                toast.error("Error al cargar datos del país");
                console.error("Error al cargar país:", err);
            });
    }, [id]);

    const handleChange = e => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const validate = () => {
        const newErrors = {};
        if (!formData.name.trim()) newErrors.name = 'El nombre del país es obligatorio';
        if (formData.codigoIso2 && formData.codigoIso2.length > 2) newErrors.codigoIso2 = 'Máximo 2 caracteres';
        if (formData.codigoIso3 && formData.codigoIso3.length > 3) newErrors.codigoIso3 = 'Máximo 3 caracteres';
        if (formData.poblacion && formData.poblacion < 0) newErrors.poblacion = 'La población no puede ser negativa';
        if (formData.area && formData.area < 0) newErrors.area = 'El área no puede ser negativa';
        if (formData.dominioTld && !formData.dominioTld.startsWith('.')) newErrors.dominioTld = 'Debe comenzar con "." (punto)';
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = e => {
        e.preventDefault();
        if (!validate()) return;

        axios.put(`/paises/${id}`, formData)
            .then(() => {
                toast.success("País actualizado correctamente");
                navigate(`/paises/${id}`);
            })
            .catch(err => {
                toast.error("Error al actualizar país");
                console.error("Error al actualizar país:", err);
            });
    };

    return (
        <motion.div className="flex-1 overflow-auto relative z-10 bg-gray-900"
            initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
        >
            <Header title={`Editar País: ${formData.name}`} />
            <Breadcrumb items={[
                { label: 'Países', href: '/paises' },
                { label: `Editar ${formData.name}` }
            ]} />
            <main className="max-w-7xl mx-auto py-6 px-4 lg:px-8">
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <form onSubmit={handleSubmit} className="bg-gray-800 p-6 rounded-2xl shadow text-white space-y-4">
                        <h2 className="text-2xl font-bold mb-4">Editar Información</h2>
                        {[
                            { name: 'name', label: 'Nombre del país', placeholder: 'Ej: Argentina' },
                            { name: 'codigoIso2', label: 'Código ISO2', placeholder: 'Ej: AR' },
                            { name: 'codigoIso3', label: 'Código ISO3', placeholder: 'Ej: ARG' },
                            { name: 'capital', label: 'Capital', placeholder: 'Ej: Buenos Aires' },
                            { name: 'poblacion', label: 'Población', type: 'number', placeholder: 'Ej: 45000000' },
                            { name: 'area', label: 'Área (km²)', type: 'number', placeholder: 'Ej: 2780400' },
                            { name: 'idioma', label: 'Idioma', placeholder: 'Ej: Español' },
                            { name: 'moneda', label: 'Moneda', placeholder: 'Ej: Peso argentino' },
                            { name: 'dominioTld', label: 'Dominio TLD', placeholder: 'Ej: .ar' },
                            { name: 'husoHorario', label: 'Huso horario', placeholder: 'Ej: GMT-3' },
                        ].map(({ name, label, type = 'text', placeholder }) => (
                            <div key={name}>
                                <label className="block text-sm font-semibold">{label}</label>
                                <input
                                    type={type}
                                    name={name}
                                    value={formData[name]}
                                    onChange={handleChange}
                                    className="mt-1 p-2 w-full rounded bg-gray-700 text-white"
                                    placeholder={placeholder}
                                />
                                {errors[name] && (
                                    <p className="text-red-400 text-sm mt-1">{errors[name]}</p>
                                )}
                            </div>
                        ))}

                        <div>
                            <label className="block text-sm font-semibold">Continente</label>
                            <select
                                name="continente"
                                value={formData.continente}
                                onChange={handleChange}
                                className="mt-1 w-full rounded bg-gray-700 text-white p-2"
                            >
                                {CONTINENTES.map(cont => (
                                    <option key={cont} value={cont}>{cont.replace(/_/g, ' ')}</option>
                                ))}
                            </select>
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 px-4 rounded"
                        >
                            Guardar cambios
                        </button>
                    </form>

                    <div className="hidden lg:flex items-center justify-center">
                        {formData.banderaUrl ? (
                            <img
                                src={formData.banderaUrl}
                                alt={`Bandera de ${formData.name}`}
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
        </motion.div>
    );
};

export default PaisEditPage;
