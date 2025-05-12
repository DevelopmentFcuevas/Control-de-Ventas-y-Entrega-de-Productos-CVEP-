import React from 'react';
import { ChevronRight } from 'lucide-react';
import { Link } from 'react-router-dom';

const Breadcrumb = ({ items }) => {
    return (
        <nav className="flex text-sm text-gray-400 mb-6" aria-label="Breadcrumb">
            <ol className="inline-flex flex-wrap items-center space-x-1">
                {items.map((item, index) => (
                    <li key={index} className="inline-flex items-center">
                        {item.href ? (
                            <Link to={item.href} className="hover:text-indigo-400 flex items-center">
                                {item.label}
                            </Link>
                        ) : (
                            <span className="text-gray-200">{item.label}</span>
                        )}
                        {index < items.length - 1 && (
                            <ChevronRight className="w-4 h-4 mx-1" />
                        )}
                    </li>
                ))}
            </ol>
        </nav>
    );
};

export default Breadcrumb;
