// 📦 Librerías externas
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';                                     // Navegación interna con React Router
import { motion } from "framer-motion";                                             // Librería para animaciones
import dayjs from 'dayjs';                                                          // Para manejar fechas fácilmente
import toast from 'react-hot-toast';
// 📁 Íconos u otros recursos externos
import { Flag, FlagOff, LandPlot, Goal } from "lucide-react";                       // Íconos
import worldGlobe from '../../assets/world-globe.png';                              // Imagen de ejemplo
// 🔧 Servicios (API, helpers, utilidades)
import axios, { getDepartamentosPorEstado, getDepartamentosPorFecha } from '../../services/api';    // Cliente Axios centralizado
// 🧩 Componentes comunes
import Header from '../../components/common/Header';                                    // Título de la sección
import DepartamentoSection from '../../components/departamentos/DepartamentoSection';   // Sección personalizada para departamento.
import StatCard from '../../components/common/StatCard';                                // Tarjetas de estadísticas
import Breadcrumb from '../../components/common/Breadcrumb';                            // Migas de pan para la Ruta de navegación
// Componentes específicos


/**
 * Página Crear Departamento que muestra el formulario de departamentos junto con estadísticas rápidas.
 * Se encarga de guardar datos de departamento hacia la API.
 */
const DepartamentoCreatePage = () => {
    
    const navigate = useNavigate();

    // Estado para mostrar mensajes globales al usuario (éxito o error)
    const [message, setMessage] = useState({ type: '', text: '' });

    // Estado para mostrar la lista de paises del Select.
    const [paises, setPaises] = useState([]);
    useEffect(() => {
        const fetchPaises = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/paises')
                setPaises(response.data);
            } catch (error) {
                console.error('Error al obtener países:', error);
                toast.error('No se pudieron cargar los países');
            }
        };

        fetchPaises();
    }, []);

    // Estado para almacenar estadísticas generales sobre los países.
    // Se actualiza con datos obtenidos desde la API al cargar el componente.
    // Se usa para mostrar las tarjetas estadísticas en la parte superior de la vista.
    const [stats, setStats] = useState({
        totalDepartamentos: 0,
        newDepartamentosToday: 0,
        activeDepartamentos: 0,
        inactiveDepartamentos: 0,
    });

    // useEffect que se ejecuta al cargar la página para obtener datos de resumen desde la API
    useEffect(() => {
        const fetchStats = async () => {
            try {
                const [activosRes, inactivosRes, hoyRes] = await Promise.all([
                    getDepartamentosPorEstado("ACTIVO"),
                    getDepartamentosPorEstado("INACTIVO"),
                    getDepartamentosPorFecha(dayjs().format('YYYY-MM-DD')),
                ]);

                // Validamos los datos esperados
                if (typeof activosRes.data !== 'number' || typeof inactivosRes.data !== 'number') {
                    console.error("[ESTADÍSTICAS] Respuesta no válida del servidor:", { activosRes, inactivosRes });
                    setMessage({ 
                        type: 'error', 
                        text: 'Los datos de departamentos activos o inactivos no son numéricos.' 
                    });
                }

                // Calculamos el total
                const total = activosRes.data + inactivosRes.data;

                setStats({
                    totalDepartamentos: total,
                    newDepartamentosToday: hoyRes.data,
                    activeDepartamentos: activosRes.data,
                    inactiveDepartamentos: inactivosRes.data,
                });
            } catch (error) {
                console.error("[ESTADÍSTICAS] Error al obtener estadísticas:", error);
                setMessage({ 
                    type: 'error',  
                    text: 'Hubo un problema al cargar las estadísticas de Departamentos. Por favor, intenta nuevamente más tarde.' 
                });
            }
        };

        fetchStats();
    }, []);
    
    
    // 📊 Estado del formulario con los campos del departamento a crear.
    // Este estado mantiene los valores que el usuario ingresa en el formulario.
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

    // 🌍 Lista de regiones válidos (para el select)
    const REGIONES = ['ORIENTAL',
        'OCCIDENTAL', 
        'SIN_ESPECIFICAR'
    ];
    
    // 📌 Maneja los cambios en los campos del formulario
    const handleChange = (e) => {
        //setForm({ ...form, [e.target.name]: e.target.value });
        
        const { name, value } = e.target;
        if (name === 'pais.id') {
            setForm({ ...form, pais: { id: value } });
        } else {
            setForm({ ...form, [name]: value });
        }
    };

    // ❗ Estado para guardar los errores del formulario, clave: nombre del campo.
    // Guarda mensajes de error específicos para cada campo del formulario.
    const [errors, setErrors] = useState({});

    // Estado para indicar si se está realizando una operación (como guardar)
    // Permite deshabilitar el botón mientras se guarda para evitar múltiples envíos.
    const [loading, setLoading] = useState(false);
    
    // ✅ Función para validar los campos del formulario antes de enviarlos al servidor.
    // Retorna `true` si todos los campos son válidos, `false` en caso contrario.
    /* const validateForm = () => {
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
    }; */
    const validateForm = () => {
        const newErrors = {};

        // Helper para detectar solo espacios o strings vacíos
        const isBlank = (value) => !value || value.trim() === '';

        // Nombre del departamento (obligatorio, solo letras, espacios y guiones)
        if (isBlank(form.name)) {
            newErrors.name = 'Por favor, ingresa el nombre del departamento.';
        } else if (!/^[\p{L}\s'-]{2,255}$/u.test(form.name.trim())) {
            newErrors.name = 'El nombre contiene caracteres inválidos o excede los 255 caracteres.';
        }

        // Código ISO (opcional, pero si lo llena, validar)
        if (!isBlank(form.codigoIso)) {
            if (!/^[A-Z]{2}$/.test(form.codigoIso.trim())) {
                newErrors.codigoIso = 'Debe tener exactamente 2 letras mayúsculas sin espacios.';
            }
        }

        // Capital (opcional, pero si lo llena, validar)
        if (!isBlank(form.capital)) {
            if (!/^[\p{L}\s'-]{2,100}$/u.test(form.capital.trim())) {
                newErrors.capital = 'La capital contiene caracteres inválidos o es muy larga.';
            }
        }

        // Población (opcional, pero válida si se ingresa)
        if (!isBlank(form.poblacion)) {
            const poblacionNum = Number(form.poblacion);
            if (isNaN(poblacionNum) || poblacionNum < 0 || poblacionNum > 2_000_000_000) {
                newErrors.poblacion = 'Ingresa una población válida (0 - 2 mil millones).';
            }
        }

        // Superficie (opcional, pero válida si se ingresa)
        if (!isBlank(form.superficie)) {
            const superficieNum = Number(form.superficie);
            if (isNaN(superficieNum) || superficieNum < 0 || superficieNum > 20_000_000) {
                newErrors.area = 'Ingresa un valor de superficie válido (0 - 20 millones km²).';
            }
        }
    
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };
     
    // 🚀 Maneja el envío del formulario
    const handleSubmit = async (e) => {
        
        e.preventDefault();

        setMessage({ type: '', text: '' }); // Limpiar mensaje anterior
        if (!validateForm()) {
            setMessage({ 
                type: 'error', 
                text: 'Corrige los errores del formulario antes de continuar.' 
            });
            return;
        }

        setLoading(true);

        try {
            //Convertir algunos campos a mayúsculas automáticamente antes de enviar.
            const sanitizedForm = {
                ...form,
                name: form.name.trim(),
                codigoIso: form.codigoIso.trim().toUpperCase(),
            };

            await axios.post('/departamentos', sanitizedForm);
            //toast.success('Departamento creado con éxito');
            //navigate('/departamentos');
            setMessage({ 
                type: 'success', 
                text: '¡El Departamento se creó correctamente!' 
            });
            navigate('/departamentos');
        } catch (error) {
            //toast.error('Error al crear el departamento');
            //console.error('Error al crear el departamento' + error);
            console.error('Error en handleSubmit - No se pudo crear el departamento:', error);
            setMessage({ 
                type: 'error', 
                text: 'Ocurrió un error al crear el departamento. Intenta nuevamente más tarde.' 
            });
        } finally {
            setLoading(false);
        }
    };

    
    return (
        <div className='flex-1 overflow-auto relative z-10 bg-gray-900'>
			
            {/* 🧭 Header superior de la página(Cabecera con título) */}
            <Header title='Crear Nuevo Departamento' />

            {/* 🧷 Breadcrumb(Migas de pan para la Ruta de navegación) */}
            <Breadcrumb items={[
                { label: 'Departamentos', href: '/departamentos' },
                { label: 'Crear nuevo departamento' }
            ]} />

            {/* 🧾 Formulario */}
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
                            
                            <h2 className="text-2xl font-bold mb-4">Guardar Información</h2>

                            {/* 🛎️ Mensajes de estado */}
                            {message.text && (
                                <div className={`mt-4 p-4 rounded-md text-white font-medium ${
                                    message.type === 'success' ? 'bg-green-600' : 'bg-red-600'
                                }`}>
                                    {message.text}
                                </div>
                            )}

                            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">

                                {/* 🧱 Campos individuales generados dinámicamente */}
                                {[
                                    { name: 'name', label: 'Nombre del departamento', placeholder: 'Ej: Alto Paraná', maxLength: 50, pattern:"^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s-]+$" },
                                    { name: 'codigoIso', label: 'Código ISO', placeholder: 'Ej: AP', maxLength: 2, inputMode: 'text' },
                                    { name: 'capital', label: 'Capital', placeholder: 'Ej: Ciudad del Este', maxLength: 50 },
                                    { name: 'poblacion', label: 'Población', type: 'number', placeholder: 'Ej: 45000000', inputMode: 'numeric', min: 0 },
                                    { name: 'superficie', label: 'Superficie (km²)', type: 'number', placeholder: 'Ej: 2780400', inputMode: 'numeric', min: 0 },
                                ].map(({ name, label, type = 'text', placeholder, maxLength, pattern, inputMode, min }) => (
                                    <div key={name}>
                                        <label title={label} className="text-lg font-semibold text-gray-100">{label}</label>
                                        <input
                                            type={type}
                                            name={name}
                                            value={form[name]}
                                            onChange={handleChange}
                                            className="mt-1 w-full rounded-md bg-gray-700 text-white p-2 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                            placeholder={placeholder}
                                            maxLength={maxLength}
                                            pattern={pattern}
                                            inputMode={inputMode}
                                            min={min}
                                        />
                                        {errors[name] && (
                                            <p className="text-red-400 text-sm mt-1">{errors[name]}</p>
                                        )}
                                    </div>
                                ))}
                            </div>

                            {/* 🌍 Selector de Region */}
                            <div>
                                <label className="text-sm text-gray-300">Region</label>
                                <select
                                    name="region"
                                    value={form.region}
                                    onChange={handleChange}
                                    className="mt-1 w-full rounded-md bg-gray-700 text-white p-2 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                                >
                                    {/* {[
                                        'ORIENTAL', 'OCCIDENTAL',
                                        'SIN_ESPECIFICAR',
                                    ].map((value) => (
                                        <option key={value} value={value}>{value.replace(/_/g, ' ')}</option>
                                    ))} */}
                                    {REGIONES.map((value) => (
                                        <option key={value} value={value}>{value.replace(/_/g, ' ')}</option>
                                    ))}
                                </select>
                            </div>
                            
                            {/* 🌍 Selector de Pais */}
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
                                {/* {errors.pais && (
                                    <p className="text-red-400 text-sm mt-1">{errors.pais}</p>
                                )} */}
                            </div>

                           {/* ✅ Botón de envío */}
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
