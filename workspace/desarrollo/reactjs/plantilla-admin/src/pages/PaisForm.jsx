import React, { useEffect, useState } from 'react';
import { useNavigate/*, Link*/ } from 'react-router-dom';
import toast from 'react-hot-toast';
import Header from '../components/common/Header';
import { motion } from "framer-motion";
import { Flag, FlagOff, LandPlot, Goal/*, ChevronRight*/ } from "lucide-react";
import PaisSection from '../components/users/PaisSection';
import axios, { getPaisesPorEstado, getPaisesPorFecha } from '../services/api';
import dayjs from 'dayjs'; // Para manejar fechas fácilmente
import StatCard from '../components/common/StatCard'; // Tarjetas de estadísticas
import worldGlobe from '../assets/world-globe.png';
import Breadcrumb from '../components/common/Breadcrumb';

const PaisForm = () => {

    const navigate = useNavigate();

    const [form, setForm] = useState({
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

    // Upload de imagen (bandera)
    const [bandera, setBandera] = useState(null);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const [loading, setLoading] = useState(false);

    const [errors, setErrors] = useState({});

    const [stats, setStats] = useState({
        totalPaises: 0,
        newPaisesToday: 0,
        activePaises: 0,
        inactivePaises: 0,
    });

    const validateForm = () => {
        const newErrors = {};
    
        if (!form.name.trim()) {
            newErrors.name = 'El nombre del país es obligatorio';
        }

        if (form.codigoIso2 && form.codigoIso2.length > 2) {
            newErrors.codigoIso2 = 'Máximo 2 caracteres';
        }

        if (form.codigoIso3 && form.codigoIso3.length > 3) {
            newErrors.codigoIso3 = 'Máximo 3 caracteres';
        }

        if (form.poblacion && form.poblacion < 0) {
            newErrors.poblacion = 'La población no puede ser negativa';
        }

        if (form.area && form.area < 0) {
            newErrors.area = 'El área no puede ser negativa';
        }

        if (form.dominioTld && !form.dominioTld.startsWith('.')) {
            newErrors.dominioTld = 'Debe comenzar con "." (punto)';
        }
    
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };
    
    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateForm()) return;

        setLoading(true);

        /**/
        //const formData = new FormData();
        // 1) La parte JSON, clave “data”
        //formData.append(
        //    'data',
        //    new Blob([JSON.stringify(form)], { type: 'application/json' })
        //);
        // 2) La imagen
        //if (bandera) {
        //    formData.append('image', bandera);
        //}
        /**/

        try {
            
            //await axios.post('/api/paises', form);
            //await api.post('/paises', form);
            await axios.post('/paises', form);
            toast.success('País creado con éxito');
            navigate('/paises');
            
            
            /*
            const formData = new FormData();
            Object.entries(form).forEach(([key, value]) => {
                formData.append(key, value);
            });
            if (bandera) {
                formData.append('bandera', bandera);
            }
            await axios.post('/paises', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            */
            
            /*
            const formData = new FormData();
            
            // Convertir el objeto form a JSON string
            const formJson = JSON.stringify(form);

            // Append como parte "data"
            //formData.append("data", new Blob([formJson], { type: 'application/json' }));
            const jsonBlob = new Blob([JSON.stringify(form)], { type: 'application/json' });
            formData.append('data', jsonBlob);

            // Adjuntar imagen si existe
            if (bandera) {
                formData.append("image", bandera);
            }

            // Enviar a backend
            //await axios.post('/paises', formData, {
            //    headers: {
            //        'Content-Type': 'multipart/form-data',
            //    },
            //});
            await axios.post('/paises', formData);

            toast.success('País creado con éxito');
            navigate('/paises');
            */

            /*
            //await api.post('/paises', formData);
            await axios.post('/paises', formData);
            toast.success('País creado con éxito');
            */
        navigate('/paises');

        } catch (error) {
            toast.error('Error al crear el país');
            console.error('Error al crear el país' + error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const [activosRes, inactivosRes, hoyRes] = await Promise.all([
                    getPaisesPorEstado("ACTIVO"),
                    getPaisesPorEstado("INACTIVO"),
                    getPaisesPorFecha(dayjs().format('YYYY-MM-DD')),
                ]);

                if (typeof activosRes.data !== 'number' || typeof inactivosRes.data !== 'number') {
                    //throw new Error("La respuesta del servidor no es válida.");
                }

                const total = activosRes.data + inactivosRes.data;
                //console.log("total: " + total + " activosRes: " + activosRes.data + " inactivosRes: " + inactivosRes.data + " hoyRes: " + hoyRes.data);

                setStats({
                    totalPaises: total,
                    newPaisesToday: hoyRes.data,
                    activePaises: activosRes.data,
                    inactivePaises: inactivosRes.data,
                });
            } catch (error) {
                console.error("Error al obtener estadísticas:", error);
                //setError("No se pudieron cargar las estadísticas. Intente más tarde.");
                //toast.error("No se pudieron cargar las estadísticas. Intenta más tarde.");
            }
        };

        fetchStats();
    }, []);


    return (
        <div className='flex-1 overflow-auto relative z-10 bg-gray-900'>
			
            {/* Header superior de la página */}
            <Header title='Crear Nuevo País' />

            {/* Breadcrumb */}
            <Breadcrumb items={[
                { label: 'Países', href: '/paises' },
                { label: 'Crear nuevo país' }
            ]} />

			<main className='max-w-7xl mx-auto py-6 px-4 lg:px-8'>
				
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
                
                <PaisSection icon={Flag} title={"Crear Nuevo País"}>
                    
                    {/* 
                    <div className='flex flex-col sm:flex-row items-center mb-6'>
                        <form onSubmit={handleSubmit} className="space-y-4"> 
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
                                    {/* <label className="block text-sm font-medium text-gray-700">{label}</label> /}
                                    <label title={label} className="text-lg font-semibold text-gray-100">{label}</label>
                                    <input
                                        type={type}
                                        name={name}
                                        value={form[name]}
                                        onChange={handleChange}
                                        className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
                                        placeholder={placeholder}
                                    />
                                    {errors[name] && (
                                        <p className="text-red-400 text-sm mt-1">{errors[name]}</p>
                                    )}
                                </div>
                            ))}

                            {/* Continente (select) /}
                            <div>
                                {/* <label className="block text-sm font-medium text-gray-700">Continente</label> /}
                                <label className="text-lg font-semibold text-gray-100">Continente</label>
                                <select
                                    name="continente"
                                    value={form.continente}
                                    onChange={handleChange}
                                    /* className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2" /
                                    className="mt-1 block w-full bg-gray-700 text-white border border-gray-600 rounded-md shadow-sm p-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                >
                                    {[
                                    'ASIA', 'AFRICA', 'AMERICA_DEL_NORTE', 'AMERICA_DEL_SUR',
                                    'ANTARTIDA', 'EUROPA', 'OCEANIA', 'SIN_ESPECIFICAR',
                                    ].map((value) => (
                                    <option key={value} value={value}>{value.replace(/_/g, ' ')}</option>
                                    ))}
                                </select>
                            </div>
                            <div>
                                <label className="text-lg font-semibold text-gray-100">Bandera</label>
                                <input
                                    type="file"
                                    accept="image/*"
                                    onChange={(e) => setBandera(e.target.files[0])}
                                    className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2 bg-gray-50 text-gray-900"
                                />
                            </div>
                            <div className="flex justify-end">
                                <button
                                    type="submit"
                                    /* className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded" /
                                    /* className='bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded transition duration-200 w-full sm:w-auto' /
                                    className='bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded transition duration-200 w-full sm:w-auto disabled:opacity-50'
                                    disabled={loading}
                                >
                                    {/* Guardar /}
                                    {loading ? 'Guardando...' : 'Guardar'}
                                </button>
                            </div>
                        </form>
                    </div> 
                    */}

                    <div className='grid grid-cols-1 lg:grid-cols-2 gap-6'>
                        
                        {/* Formulario a la izquierda */}
                        <form onSubmit={handleSubmit} className="space-y-6 bg-gray-800 p-6 rounded-2xl shadow-md"> 
                            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                            
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
                                        <label title={label} className="text-lg font-semibold text-gray-100">{label}</label>
                                        <input
                                            type={type}
                                            name={name}
                                            value={form[name]}
                                            onChange={handleChange}
                                            className="mt-1 w-full rounded-md bg-gray-700 text-white p-2 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                            placeholder={placeholder}
                                        />
                                        {errors[name] && (
                                            <p className="text-red-400 text-sm mt-1">{errors[name]}</p>
                                        )}
                                    </div>
                                ))}
                            </div>

                            {/* Continente (select) */}
                            <div>
                                <label className="text-sm text-gray-300">Continente</label>
                                <select
                                    name="continente"
                                    value={form.continente}
                                    onChange={handleChange}
                                    className="mt-1 w-full rounded-md bg-gray-700 text-white p-2 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                >
                                    {[
                                        'ASIA', 'AFRICA', 'AMERICA_DEL_NORTE', 'AMERICA_DEL_SUR',
                                        'ANTARTIDA', 'EUROPA', 'OCEANIA', 'SIN_ESPECIFICAR',
                                    ].map((value) => (
                                        <option key={value} value={value}>{value.replace(/_/g, ' ')}</option>
                                    ))}
                                </select>
                            </div>

                            {/* Archivo de Bandera */}
                            <div>
                                <label className="text-sm text-gray-300">Bandera</label>
                                <input
                                    type="file"
                                    accept="image/*"
                                    onChange={(e) => setBandera(e.target.files[0])}
                                    className="mt-1 w-full rounded-md bg-gray-50 p-2 text-gray-800 border border-gray-300"
                                />
                            </div>
                            {bandera && (
                                <img
                                    src={URL.createObjectURL(bandera)}
                                    alt="Vista previa"
                                    className="mt-2 w-32 h-auto rounded shadow"
                                />
                            )}

                            {/* Botón */}
                            <div className="flex justify-end">
                                <button
                                    type="submit"
                                    className='bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-2 rounded-lg font-bold shadow-md transition'
                                    disabled={loading}
                                >
                                    {/* Guardar */}
                                    {loading ? 'Guardando...' : 'Guardar'}
                                </button>
                            </div>
                        </form>

                        {/* Imagen estática a la derecha */}
                        <div className="hidden lg:flex items-center justify-center">
                            <img
                                src={worldGlobe}
                                alt="Ilustración mundo"
                                className="w-3/4 max-w-sm opacity-80"
                            />
                        </div>

                    </div>

		        </PaisSection>
			</main>
		</div>
    )
}

export default PaisForm;