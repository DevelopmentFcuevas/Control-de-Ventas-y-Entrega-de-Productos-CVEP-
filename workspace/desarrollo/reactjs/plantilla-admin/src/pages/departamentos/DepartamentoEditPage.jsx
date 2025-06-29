import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from '../../services/api';
import toast from 'react-hot-toast';
import { motion } from 'framer-motion';
import Header from '../../components/common/Header';
import Breadcrumb from '../../components/common/Breadcrumb';
import worldGlobe from '../../assets/world-globe.png';

const REGIONES = ['ORIENTAL', 'OCCIDENTAL', 'SIN_ESPECIFICAR'];

const DepartamentoEditPage = () => {

  const { id } = useParams();

  const navigate = useNavigate();

  const [paises, setPaises] = useState([]);

  const [formData, setFormData] = useState({
    name: '',
    codigoIso: '',
    capital: '',
    poblacion: '',
    superficie: '',
    region: 'SIN_ESPECIFICAR',
    pais: { id: '' }
  });

  const [errors, setErrors] = useState({});

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

  const validate = () => {
      const newErrors = {};

      if (!formData.name.trim()) newErrors.name = 'El nombre del departamento es obligatorio';
      if (formData.codigoIso && formData.codigoIso.length > 2) newErrors.codigoIso = 'Máximo 2 caracteres';
      if (formData.poblacion && formData.poblacion < 0) newErrors.poblacion = 'La población no puede ser negativa';
      if (formData.superficie && formData.superficie < 0) newErrors.superficie = 'La Superficie no puede ser negativa';
      if (!formData.pais.id) newErrors.pais = 'Debe seleccionar un país';

      setErrors(newErrors);
      
      return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = e => {
    e.preventDefault();
    if (!validate()) return;

    axios.put(`/departamentos/${id}`, formData)
      .then(() => {
        toast.success("Departamento actualizado correctamente");
        navigate(`/departamentos/${id}`);
      })
      .catch(err => {
          toast.error("Error al actualizar departamento");
          console.error("Error al actualizar departamento:", err);
      });
  };


  return (
        <motion.div className="flex-1 overflow-auto relative z-10 bg-gray-900"
            initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
        >
            <Header title={`Editar Departamento: ${formData.name}`} />
            <Breadcrumb items={[
                { label: 'Departamentos', href: '/departamentos' },
                { label: `Editar ${formData.name}` }
            ]} />
            <main className="max-w-7xl mx-auto py-6 px-4 lg:px-8">
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <form onSubmit={handleSubmit} className="bg-gray-800 p-6 rounded-2xl shadow text-white space-y-4">
                        <h2 className="text-2xl font-bold mb-4">Editar Información</h2>
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
                            <label className="block text-sm font-semibold">Region</label>
                            <select
                                name="region"
                                value={formData.region}
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
                              value={formData.pais.id}
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

                        <button
                            type="submit"
                            className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 px-4 rounded"
                        >
                            Guardar cambios
                        </button>
                    </form>

                    <div className="hidden lg:flex items-center justify-center">
                        <img src={worldGlobe} alt="Ilustración mundo" className="w-3/4 max-w-sm opacity-80" />
                    </div>
                </div>
            </main>
        </motion.div>
    );
}

export default DepartamentoEditPage;