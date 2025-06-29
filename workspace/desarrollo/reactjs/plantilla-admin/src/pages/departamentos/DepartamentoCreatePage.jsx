import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import axios, { getDepartamentosPorEstado, getDepartamentosPorFecha } from '../../services/api';
import dayjs from 'dayjs'; // Para manejar fechas fácilmente
import Header from '../../components/common/Header';
import Breadcrumb from '../../components/common/Breadcrumb';
import { motion } from "framer-motion";
import { Flag, FlagOff, LandPlot, Goal } from "lucide-react";
import StatCard from '../../components/common/StatCard'; // Tarjetas de estadísticas
import DepartamentoSection from '../../components/departamentos/DepartamentoSection';
import worldGlobe from '../../assets/world-globe.png';

const DepartamentoCreatePage = () => {
    
    const navigate = useNavigate();

    const [paises, setPaises] = useState([]);

    useEffect(() => {
        const fetchPaises = async () => {
            try {
                //const response = await axios.get('/paises');
                const response = await axios.get('http://localhost:8080/api/paises')
                setPaises(response.data);
            } catch (error) {
                console.error('Error al obtener países:', error);
                toast.error('No se pudieron cargar los países');
            }
        };

        fetchPaises();
    }, []);

    const [form, setForm] = useState({
        name: '',
        codigoIso: '',
        capital: '',
        poblacion: '',
        superficie: '',
        region: 'SIN_ESPECIFICAR',
        // pais: '',
        pais: { id: '' },
    });

    const handleChange = (e) => {
        //setForm({ ...form, [e.target.name]: e.target.value });
        
        const { name, value } = e.target;
        if (name === 'pais.id') {
            setForm({ ...form, pais: { id: value } });
        } else {
            setForm({ ...form, [name]: value });
        }
    };

    const [loading, setLoading] = useState(false);
    
    const [errors, setErrors] = useState({});
    
    const [stats, setStats] = useState({
        totalDepartamentos: 0,
        newDepartamentosToday: 0,
        activeDepartamentos: 0,
        inactiveDepartamentos: 0,
    });

    const validateForm = () => {
        const newErrors = {};
    
        if (!form.name.trim()) {
            newErrors.name = 'El nombre del departamento es obligatorio';
        }

        if (form.codigoIso && form.codigoIso.length > 2) {
            newErrors.codigoIso = 'Máximo 2 caracteres';
        }

        if (form.poblacion && form.poblacion < 0) {
            newErrors.poblacion = 'La población no puede ser negativa';
        }

        if (form.superficie && form.superficie < 0) {
            newErrors.superficie = 'La superficie no puede ser negativa';
        }

        if (!form.pais) {
            newErrors.pais = 'Debe seleccionar un país';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateForm()) return;

        setLoading(true);

        try {
            await axios.post('/departamentos', form);
            toast.success('Departamento creado con éxito');
            navigate('/departamentos');
        } catch (error) {
            toast.error('Error al crear el departamento');
            console.error('Error al crear el departamento' + error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const [activosRes, inactivosRes, hoyRes] = await Promise.all([
                    getDepartamentosPorEstado("ACTIVO"),
                    getDepartamentosPorEstado("INACTIVO"),
                    getDepartamentosPorFecha(dayjs().format('YYYY-MM-DD')),
                ]);

                if (typeof activosRes.data !== 'number' || typeof inactivosRes.data !== 'number') {
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
                //setError("No se pudieron cargar las estadísticas. Intente más tarde.");
                //toast.error("No se pudieron cargar las estadísticas. Intenta más tarde.");
            }
        };

        fetchStats();
    }, []);

    return (
        <div className='flex-1 overflow-auto relative z-10 bg-gray-900'>
			
            {/* Header superior de la página */}
            <Header title='Crear Nuevo Departamento' />

            {/* Breadcrumb */}
            <Breadcrumb items={[
                { label: 'Departamentos', href: '/departamentos' },
                { label: 'Crear nuevo departamento' }
            ]} />

			<main className='max-w-7xl mx-auto py-6 px-4 lg:px-8'>
				
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
                
                <DepartamentoSection icon={Flag} title={"Crear Nuevo Departamento"}>

                    <div className='grid grid-cols-1 lg:grid-cols-2 gap-6'>
                        
                        {/* Formulario a la izquierda */}
                        <form onSubmit={handleSubmit} className="space-y-6 bg-gray-800 p-6 rounded-2xl shadow-md"> 
                            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                                {[
                                    { name: 'name', label: 'Nombre del departamento', placeholder: 'Ej: Alto Paraná' },
                                    { name: 'codigoIso', label: 'Código ISO', placeholder: 'Ej: AP' },
                                    { name: 'capital', label: 'Capital', placeholder: 'Ej: Ciudad del Este' },
                                    { name: 'poblacion', label: 'Población', type: 'number', placeholder: 'Ej: 45000000' },
                                    { name: 'superficie', label: 'Superficie (km²)', type: 'number', placeholder: 'Ej: 2780400' },
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

                            {/* Region (select) */}
                            <div>
                                <label className="text-sm text-gray-300">Region</label>
                                <select
                                    name="region"
                                    value={form.region}
                                    onChange={handleChange}
                                    className="mt-1 w-full rounded-md bg-gray-700 text-white p-2 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                >
                                    {[
                                        'ORIENTAL', 'OCCIDENTAL',
                                        'SIN_ESPECIFICAR',
                                    ].map((value) => (
                                        <option key={value} value={value}>{value.replace(/_/g, ' ')}</option>
                                    ))}
                                </select>
                            </div>
                            
                            {/* Pais (select) */}
                            <div>
                                <label className="text-sm text-gray-300">País</label>
                                <select
                                    // name="pais"
                                    // value={form.pais}
                                    name="pais.id"
                                    value={form.pais.id}
                                    onChange={handleChange}
                                    className="mt-1 w-full rounded-md bg-gray-700 text-white p-2 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                    required
                                >
                                    <option value="">Seleccione un país</option>
                                    {paises.map((pais) => (
                                        <option key={pais.id} value={pais.id}>
                                            {pais.name}
                                        </option>
                                    ))}
                                </select>
                                {errors.pais && (
                                    <p className="text-red-400 text-sm mt-1">{errors.pais}</p>
                                )}
                            </div>

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

		        </DepartamentoSection>
			</main>
		</div>
    )
}

export default DepartamentoCreatePage;
