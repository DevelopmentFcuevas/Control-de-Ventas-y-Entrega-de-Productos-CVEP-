import React from 'react';

// Componente Header que recibe una prop "title" y la muestra como título principal de la página
const Header = ({ title }) => {
  return (
    // Contenedor del header con fondo semitransparente, desenfoque y borde inferior
    <header className='bg-gray-800 bg-opacity-50 backdrop-blur-md shadow-lg border-b border-gray-700'>

        {/* Wrapper para centrar el contenido y darle paddings responsive */}
        <div className='max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8'>

            {/* Título recibido por props */}
            <h1 className='text-2xl font-semibold text-gray-100'>{title}</h1>
        </div>
    </header>
  )
}

/* 
🧠 Sugerencias para crecer con este componente:
------------------------------------------------

* En Header, podrías incluir botones de acciones (como "Agregar nuevo", "Filtrar", etc.) si lo 
necesitás más adelante.
*/

export default Header;