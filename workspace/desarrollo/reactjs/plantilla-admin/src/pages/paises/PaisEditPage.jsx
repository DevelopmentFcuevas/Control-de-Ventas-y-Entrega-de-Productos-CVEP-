// 📦 Librerías externas
import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';                          // Navegación interna con React Router
import dayjs from 'dayjs';                                                          // Para manejar fechas fácilmente
import { motion } from 'framer-motion';                                             // Librería para animaciones
// 📁 Íconos u otros recursos externos
import { Flag, FlagOff, LandPlot, Goal } from "lucide-react";                       // Íconos
import worldGlobe from '../../assets/world-globe.png';                              // Imagen de ejemplo
// 🔧 Servicios (API, helpers, utilidades)
import axios, { getPaisesPorEstado, getPaisesPorFecha } from '../../services/api';  // Cliente Axios centralizado
// 🧩 Componentes comunes
import Header from '../../components/common/Header';                                // Título de la sección
import PaisSection from '../../components/paises/PaisSection';                      // Sección personalizada para pais.
import StatCard from '../../components/common/StatCard';                            // Tarjetas de estadísticas
import Breadcrumb from '../../components/common/Breadcrumb';                        // Migas de pan para la Ruta de navegación
// Componentes específicos


/**
 * 📝 Página de edición de un país.
 */
const PaisEditPage = () => {

    // 📥 Extrae el ID de la URL para saber qué país editar
    const { id } = useParams();

    // 🔁 Navegación programática tras guardar
    const navigate = useNavigate();

    // Estado para mostrar mensajes globales al usuario (éxito o error)
    const [message, setMessage] = useState({ type: '', text: '' });

    // 📡 Cargar datos actuales del país al montar el componente
    useEffect(() => {
        axios.get(`/paises/${id}`)
            .then(res => {
                // Limpiar valores nulos y convertirlos en cadenas vacías
                const sanitized = Object.fromEntries(
                    Object.entries(res.data).map(([key, value]) => [key, value ?? ''])
                );
                setForm(sanitized);
            })
            .catch(err => {
                //toast.error("Error al cargar datos del país");
                //console.error("Error al cargar país:", err);

                console.error("[❌ ERROR] No se pudo obtener datos del país:", err);
                setMessage({
                    type: 'error',
                    text: 'Ocurrió un error al cargar los datos del país. Por favor, intenta nuevamente.',
                });
            });
    }, [id]);

    /*
    // Estado para almacenar estadísticas generales sobre los países.
    // Se actualiza con datos obtenidos desde la API al cargar el componente.
    // Se usa para mostrar las tarjetas estadísticas en la parte superior de la vista.
    const [stats, setStats] = useState({
        totalPaises: 0,
        newPaisesToday: 0,
        activePaises: 0,
        inactivePaises: 0,
    });

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const [activosRes, inactivosRes, hoyRes] = await Promise.all([
                    getPaisesPorEstado("ACTIVO"),
                    getPaisesPorEstado("INACTIVO"),
                    getPaisesPorFecha(dayjs().format('YYYY-MM-DD')),
                ]);

                // Validamos los datos esperados
                if (typeof activosRes.data !== 'number' || typeof inactivosRes.data !== 'number') {
                    console.error("[ESTADÍSTICAS] Respuesta no válida del servidor:", { activosRes, inactivosRes });
                    setMessage({ 
                        type: 'error', 
                        text: 'Los datos de países activos o inactivos no son numéricos.' 
                    });
                }

                // Calculamos el total
                const total = activosRes.data + inactivosRes.data;

                setStats({
                    totalPaises: total,
                    newPaisesToday: hoyRes.data,
                    activePaises: activosRes.data,
                    inactivePaises: inactivosRes.data,
                });
            } catch (error) {
                console.error("[ESTADÍSTICAS] Error al obtener estadísticas:", error);
                setMessage({ 
                    type: 'error', 
                    text: 'No se pudieron cargar las estadísticas. Intenta más tarde.' 
                });
            }
        };

        fetchStats();
    }, []);*/


    //  📊 Estado del formulario con los campos del país a modificar.
    // Este estado mantiene los valores que el usuario ingresa en el formulario.
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

    // 🌍 Lista de continentes válidos (para el select)
    const CONTINENTES = [
        'ASIA', 
        'AFRICA', 
        'AMERICA_DEL_NORTE', 
        'AMERICA_DEL_SUR', 
        'ANTARTIDA', 
        'EUROPA', 
        'OCEANIA', 
        'SIN_ESPECIFICAR'
    ];
    
    
    // 📌 Maneja los cambios en los campos del formulario
    const handleChange = e => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };
    
    
    // ❗ Estado para guardar los errores del formulario, clave: nombre del campo.
    // Guarda mensajes de error específicos para cada campo del formulario.
    const [errors, setErrors] = useState({});

    // Estado para indicar si se está realizando una operación (como guardar)
    // Permite deshabilitar el botón mientras se guarda para evitar múltiples envíos.
    const [loading, setLoading] = useState(false);

    // ✅ Función para validar los campos del formulario antes de enviarlos al servidor.
    // Retorna `true` si todos los campos son válidos, `false` en caso contrario.
    const validateForm = () => {
        const newErrors = {};

        // Helper para detectar solo espacios o strings vacíos
        //const isBlank = (value) => !value || value.trim() === '';
        const isBlank = (value) => {
            if (typeof value !== 'string') return !value && value !== 0;
            return value.trim() === '';
        };

        // Nombre del país (obligatorio, solo letras, espacios y guiones)
        if (isBlank(form.name)) {
            newErrors.name = 'Por favor, ingresa el nombre del país.';
        } else if (!/^[\p{L}\s'-]{2,255}$/u.test(form.name.trim())) {
            newErrors.name = 'El nombre contiene caracteres inválidos o excede los 255 caracteres.';
        }

        // Código ISO2 (opcional, pero si lo llena, validar)
        if (!isBlank(form.codigoIso2)) {
            if (!/^[A-Z]{2}$/.test(form.codigoIso2.trim())) {
                newErrors.codigoIso2 = 'Debe tener exactamente 2 letras mayúsculas sin espacios.';
            }
        }
        
        // Código ISO3 (opcional, pero si lo llena, validar)
        if (!isBlank(form.codigoIso3)) {
            if (!/^[A-Z]{3}$/.test(form.codigoIso3.trim())) {
                newErrors.codigoIso3 = 'Debe tener exactamente 3 letras mayúsculas sin espacios.';
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

        // Área (opcional, pero válida si se ingresa)
        if (!isBlank(form.area)) {
            const areaNum = Number(form.area);
            if (isNaN(areaNum) || areaNum < 0 || areaNum > 20_000_000) {
                newErrors.area = 'Ingresa un valor de área válido (0 - 20 millones km²).';
            }
        }

        // Idioma (opcional, pero válida si se ingresa)
        if (!isBlank(form.idioma)) {
            if (!/^[\p{L}\s'-]{2,100}$/u.test(form.idioma.trim())) {
                newErrors.idioma = 'El idioma contiene caracteres inválidos o es muy largo.';
            }
        }

        // Moneda (opcional, pero válida si se ingresa)
        if (!isBlank(form.moneda)) {
            if (!/^[\p{L}\s'-]{2,100}$/u.test(form.moneda.trim())) {
                newErrors.moneda = 'La moneda contiene caracteres inválidos o es muy larga.';
            }
        }

        // Dominio TLD (opcional, pero válida si se ingresa, empieza con punto y sigue con dos letras)
        if (!isBlank(form.dominioTld)) {
            if (!/^\.[a-z]{2,10}$/.test(form.dominioTld.trim())) {
                newErrors.dominioTld = 'Formato inválido. Debe comenzar con punto (.) seguido de letras (ej. .ar).';
            }
        }

        // Huso horario (opcional, pero válida si se ingresa)
        if (!isBlank(form.husoHorario)) {
            if (!/^[\w\-:+ ]{2,30}$/.test(form.husoHorario.trim())) {
                newErrors.husoHorario = 'El formato del huso horario no es válido.';
            }
        }
    
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };
    

    // 🚀 Maneja el envío del formulario
    const handleSubmit = e => {
        
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
                codigoIso2: form.codigoIso2.trim().toUpperCase(),
                codigoIso3: form.codigoIso3.trim().toUpperCase(),
                capital: form.capital.trim(),
                idioma: form.idioma.trim(),
                moneda: form.moneda.trim(),
                dominioTld: form.dominioTld.trim().toLowerCase(),
                husoHorario: form.husoHorario.trim(),
            };

            //axios.put(`/paises/${id}`, form)
            axios.put(`/paises/${id}`, sanitizedForm);
            /*.then(() => {
                //toast.success("País actualizado correctamente");
                //navigate(`/paises/${id}`); // Redirige al detalle del país

                setMessage({
                    type: 'success',
                    text: 'Los datos del país se actualizaron correctamente.',
                });
                // Redirigir tras breve pausa
                setTimeout(() => navigate(`/paises/${id}`), 1500);
            })
            .catch(err => {
                //toast.error("Error al actualizar país");
                //console.error("Error al actualizar país:", err);

                setMessage({
                    type: 'error',
                    text: 'No se pudo actualizar la información. Por favor revisa los datos o intenta más tarde.',
                });
                console.error("[❌ ERROR] Fallo al actualizar país:", err);
            });*/
            setMessage({ 
                type: 'success', 
                text: '¡El país se actualizo correctamente!' 
            });
            //navigate('/paises');
            // Redirigir tras breve pausa
            setTimeout(() => navigate(`/paises/${id}`), 5000);//1500

        } catch (error) {
            console.error('Error en handleSubmit - No se pudo actualizar el país:', error);
            setMessage({ 
                type: 'error', 
                text: 'Ocurrió un error al actualizar el país. Intenta nuevamente más tarde.' 
            });
        } finally {
            setLoading(false);
        }

    };

    return (
        <motion.div className="flex-1 overflow-auto relative z-10 bg-gray-900"
            initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
        >
            {/* 🧭 Header superior de la página(Cabecera con título) */}
            <Header title={`Editar País: ${form.name}`} />

            {/* 🧷 Breadcrumb(Migas de pan para la Ruta de navegación) */}
            <Breadcrumb items={[
                { label: 'Países', href: '/paises' },
                { label: `Editar País: ${form.name}` }
            ]} />

            {/* 🧾 Formulario */}
            <main className="max-w-7xl mx-auto py-6 px-4 lg:px-8">

                {/* Tarjetas con estadísticas rápidas */}
                {/* <motion.div
                    className='grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4 mb-8'
                    initial={{ opacity: 0, y: 200 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 1 }}
                >
                    <StatCard name="Total de Países" icon={Flag} value={stats.totalPaises.toLocaleString()} color='#6366F1' />
                    <StatCard name="Nuevos Países Agregados(hoy)" icon={LandPlot} value={stats.newPaisesToday} color='#10B981' />
                    <StatCard name="Países Activos" icon={Goal} value={stats.activePaises.toLocaleString()} color='#F59E0B' />
                    <StatCard name="Países Inactivos" icon={FlagOff} value={stats.inactivePaises} color='#EF4444' />
                </motion.div> */}
                
                <PaisSection icon={Flag} title={"Editar País"}>

                    <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                        
                        {/* ✏️ Formulario de edición */}
                        <form onSubmit={handleSubmit} className="bg-gray-800 p-6 rounded-2xl shadow text-white space-y-4">
                            
                            <h2 className="text-2xl font-bold mb-4">Editar Información</h2>
                            
                            {/* 🛎️ Mensajes de estado */}
                            {message.text && (
                                <div className={`p-3 rounded text-sm font-medium ${
                                    message.type === 'success' ? 'bg-green-600 text-white' : 'bg-red-600 text-white'
                                }`}>
                                    {message.text}
                                </div>
                            )}

                            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">

                                {/* 🧱 Campos individuales generados dinámicamente */}
                                {[
                                    { name: 'name', label: 'Nombre del país', placeholder: 'Ej: Argentina', maxLength: 50, pattern:"^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s-]+$" },
                                    { name: 'codigoIso2', label: 'Código ISO2', placeholder: 'Ej: AR', maxLength: 2, pattern: '[A-Z]{2}', inputMode: 'text' },
                                    { name: 'codigoIso3', label: 'Código ISO3', placeholder: 'Ej: ARG', maxLength: 3, pattern: '[A-Z]{3}', inputMode: 'text' },
                                    { name: 'capital', label: 'Capital', placeholder: 'Ej: Buenos Aires', maxLength: 50 },
                                    { name: 'poblacion', label: 'Población', type: 'number', placeholder: 'Ej: 45000000', inputMode: 'numeric', min: 0 },
                                    { name: 'area', label: 'Área (km²)', type: 'number', placeholder: 'Ej: 2780400', inputMode: 'numeric', min: 0 },
                                    { name: 'idioma', label: 'Idioma', placeholder: 'Ej: Español', maxLength: 30 },
                                    { name: 'moneda', label: 'Moneda', placeholder: 'Ej: Peso argentino', maxLength: 30 },
                                    { name: 'dominioTld', label: 'Dominio TLD', placeholder: 'Ej: .ar', pattern: '\\.[a-z]{2,3}', maxLength: 4 },
                                    { name: 'husoHorario', label: 'Huso horario', placeholder: 'Ej: GMT-3', pattern: 'GMT[+-]\\d{1,2}', maxLength: 6 },
                                ].map(({ name, label, type = 'text', placeholder, maxLength, pattern, inputMode, min }) => (
                                    <div key={name}>
                                        <label className="block text-sm font-semibold">{label}</label>
                                        <input
                                            type={type}
                                            name={name}
                                            value={form[name]}
                                            onChange={handleChange}
                                            className="mt-1 p-2 w-full rounded bg-gray-700 text-white"
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

                            {/* 🌍 Selector de continente */}
                            <div>
                                <label className="block text-sm font-semibold">Continente</label>
                                <select
                                    name="continente"
                                    value={form.continente}
                                    onChange={handleChange}
                                    className="mt-1 w-full rounded bg-gray-700 text-white p-2"
                                >
                                    {CONTINENTES.map(cont => (
                                        <option key={cont} value={cont}>{cont.replace(/_/g, ' ')}</option>
                                    ))}
                                </select>
                            </div>

                            {/* ✅ Botón de envío */}
                            <button
                                type="submit"
                                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 px-4 rounded"
                                disabled={loading}
                            >
                                {/* Guardar */}
                                {loading ? 'Guardando...' : 'Guardar'}
                            </button>
                        </form>

                        {/* 🖼️ Vista previa de la bandera o imagen genérica estática a la derecha */}
                        <div className="hidden lg:flex items-center justify-center">
                            {form.banderaUrl ? (
                                <img
                                    src={form.banderaUrl}
                                    alt={`Bandera de ${form.name}`}
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

                </PaisSection>
            </main>
        </motion.div>
    );
};

export default PaisEditPage;
