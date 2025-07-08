// 📦 Librerías externas
import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';          // Navegación interna con React Router
import { motion } from 'framer-motion';                             // Librería para animaciones
import toast from 'react-hot-toast';
// 📁 Íconos u otros recursos externos
import { Flag } from "lucide-react";                       // Íconos
import worldGlobe from '../../assets/world-globe.png';              // Imagen de ejemplo
// 🔧 Servicios (API, helpers, utilidades)
import axios from '../../services/api';                             // Cliente Axios centralizado
// 🧩 Componentes comunes
import Header from '../../components/common/Header';                // Título de la sección
import Breadcrumb from '../../components/common/Breadcrumb';        // Migas de pan para la Ruta de navegación
import DepartamentoSection from '../../components/departamentos/DepartamentoSection';














/**
 * 📝 Página de edición de un departamento.
 */
const DepartamentoEditPage = () => {

    // 📥 Extrae el ID de la URL para saber qué departamento editar
    const { id } = useParams();

    // 🔁 Navegación programática tras guardar
    const navigate = useNavigate();

    // Estado para mostrar mensajes globales al usuario (éxito o error)
    const [message, setMessage] = useState({ type: '', text: '' });

    const [form, setFormData] = useState({
        name: '',
        codigoIso: '',
        capital: '',
        poblacion: '',
        superficie: '',
        region: 'SIN_ESPECIFICAR',
        pais: { id: '' }
    });

    const [paises, setPaises] = useState([]);

    /*useEffect(() => {
        axios.get(`/departamentos/${id}`)
            .then(res => {
                const sanitized = Object.fromEntries(
                    Object.entries(res.data).map(([key, value]) => [key, value ?? ''])
            );
            setFormData(sanitized);
        })
        .catch(err => {
            toast.error("Error al cargar datos del departamento");
            console.error("Error al cargar departamento:", err);
        });
    }, [id]);*/
    // 📡 Cargar datos actuales del departamento al montar el componente
    useEffect(() => {
        // Traer departamento
        axios.get(`/departamentos/${id}`)
            .then(res => {
                const data = res.data;
                // Asegurarse de que 'pais' no es null
                const departamento = {
                    ...data,
                    pais: data.pais ?? { id: '' },
                    poblacion: data.poblacion ?? '',
                    superficie: data.superficie ?? '',
                };
                setFormData(departamento);
            })
            .catch(err => {
                toast.error("Error al cargar datos del departamento");
                console.error("Error al cargar departamento:", err);
            });

        // Traer lista de países
        axios.get('/paises')
            .then(res => {
                setPaises(res.data);
            })
            .catch(err => {
                toast.error("Error al cargar países");
                console.error("Error al cargar países:", err);
            });
    }, [id]);


    // 🌍 Lista de regiones válidos (para el select)
    const REGIONES = ['ORIENTAL', 'OCCIDENTAL', 'SIN_ESPECIFICAR'];

    // 📌 Maneja los cambios en los campos del formulario
    const handleChange = e => {
        //const { name, value } = e.target;
        //setFormData(prev => ({ ...prev, [name]: value }));

        const { name, value } = e.target;

        if (name === 'pais.id') {
            setFormData(prev => ({
                ...prev,
                pais: { ...prev.pais, id: parseInt(value) }
            }));
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };


    // ❗ Estado para guardar los errores del formulario, clave: nombre del campo.
    // Guarda mensajes de error específicos para cada campo del formulario.
    const [errors, setErrors] = useState({});

    // Estado para indicar si se está realizando una operación (como guardar)
    // Permite deshabilitar el botón mientras se guarda para evitar múltiples envíos.
    const [loading, setLoading] = useState(false);
    
    
    /*const validateForm = () => {
        const newErrors = {};
        if (!formData.name.trim()) newErrors.name = 'El nombre del departamento es obligatorio';
        if (formData.codigoIso && formData.codigoIso.length > 2) newErrors.codigoIso = 'Máximo 2 caracteres';
        if (formData.poblacion && formData.poblacion < 0) newErrors.poblacion = 'La población no puede ser negativa';
        if (formData.superficie && formData.superficie < 0) newErrors.superficie = 'La Superficie no puede ser negativa';
        if (!formData.pais.id) newErrors.pais = 'Debe seleccionar un país';
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };*/
    // ✅ Función para validar los campos del formulario antes de enviarlos al servidor.
    // Retorna `true` si todos los campos son válidos, `false` en caso contrario.
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

    const handleSubmit = e => {

        e.preventDefault();
        
        //if (!validateForm()) return;
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

            axios.put(`/departamentos/${id}`, sanitizedForm);
            
            setMessage({ 
                type: 'success', 
                text: '¡El departamento se actualizo correctamente!' 
            });

            setTimeout(() => navigate(`/departamentos/${id}`), 3000);

            /*axios.put(`/departamentos/${id}`, form)
            .then(() => {
                toast.success("Departamento actualizado correctamente");
                navigate(`/departamentos/${id}`);
            })
            .catch(err => {
                toast.error("Error al actualizar departamento");
                console.error("Error al actualizar departamento:", err);
            });*/

        } catch (error) {
            console.error('Error en handleSubmit - No se pudo actualizar el departamento:', error);
            setMessage({ 
                type: 'error', 
                text: 'Ocurrió un error al actualizar el departamento. Intenta nuevamente más tarde.' 
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
            <Header title={`Editar Departamento: ${form.name}`} />

            {/* 🧷 Breadcrumb(Migas de pan para la Ruta de navegación) */}
            <Breadcrumb items={[
                { label: 'Departamentos', href: '/departamentos' },
                { label: `Editar ${form.name}` }
            ]} />

            {/* 🧾 Formulario */}
            <main className="max-w-7xl mx-auto py-6 px-4 lg:px-8">

                <DepartamentoSection icon={Flag} title={"Editar Departamento"}>
                    
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
                                    { name: 'name', label: 'Nombre del departamento', placeholder: 'Ej: Argentina' },
                                    { name: 'codigoIso', label: 'Código ISO', placeholder: 'Ej: AR' },
                                    { name: 'capital', label: 'Capital', placeholder: 'Ej: Buenos Aires' },
                                    { name: 'poblacion', label: 'Población', type: 'number', placeholder: 'Ej: 45000000' },
                                    { name: 'superficie', label: 'Superficie (km²)', type: 'number', placeholder: 'Ej: 2780400' },
                                ].map(({ name, label, type = 'text', placeholder }) => (
                                    <div key={name}>
                                        <label className="block text-sm font-semibold">{label}</label>
                                        <input
                                            type={type}
                                            name={name}
                                            value={form[name]}
                                            onChange={handleChange}
                                            className="mt-1 p-2 w-full rounded bg-gray-700 text-white"
                                            placeholder={placeholder}
                                        />
                                        {errors[name] && (
                                            <p className="text-red-400 text-sm mt-1">{errors[name]}</p>
                                        )}
                                    </div>
                                ))}
                            </div>


                            {/* 🌍 Selector de region */}
                            <div>
                                <label className="block text-sm font-semibold">Region</label>
                                <select
                                    name="region"
                                    value={form.region}
                                    onChange={handleChange}
                                    className="mt-1 w-full rounded bg-gray-700 text-white p-2"
                                >
                                    {REGIONES.map(cont => (
                                        <option key={cont} value={cont}>{cont.replace(/_/g, ' ')}</option>
                                    ))}
                                </select>
                            </div>

                            <div>
                                {errors.pais && <p className="text-red-400 text-sm mt-1">{errors.pais}</p>}
                                <label className="block text-sm font-semibold">País</label>
                                <select
                                    name="pais.id"
                                    value={form.pais.id}
                                    onChange={handleChange}
                                    className="mt-1 w-full rounded bg-gray-700 text-white p-2"
                                >
                                    <option value="">Seleccione un país</option>
                                    {paises.map(pais => (
                                        <option key={pais.id} value={pais.id}>
                                            {pais.name}
                                        </option>
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
                            <img src={worldGlobe} alt="Ilustración mundo" className="w-3/4 max-w-sm opacity-80" />
                        </div>

                    </div>
                </DepartamentoSection>
                
                
            </main>
        </motion.div>
    );
}

export default DepartamentoEditPage;