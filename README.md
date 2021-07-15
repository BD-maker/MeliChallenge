# Mercadolibre App Challenge

## Extracto

El ejercicio consiste en desarrollar una app utilizando las APIs de Mercado Libre para permitirle al usuario buscar productos por nombre y luego ver los detalles del producto seleccionado. 

La app cuenta con tres pantallas:

1. Pantalla inicial con campo de búsqueda
2. Visualización de resultados de la búsqueda
3. Detalles del producto seleccionado

## Instrucciones

- Clona el repositorio `https://github.com/BD-maker/MeliChallenge.git`
- Sincroniza Gradle
- Y por último compila el proyecto


##  Arquitectura

En el proyecto se implementó Clean Arquitecture, esto permite desacoplar diferentes unidades del código para que resulte más fácil de modificar, expandir y testear.

![onion](https://miro.medium.com/max/1400/1*jH0iI7-MSQYgLUrqTUm6mg.png)

La idea es definir el proyecto en varias capas y que las exteriores sepan lo que hay en las interiores, pero en en las capas interiores no sepan lo que hay en las exteriores. Por este motivo las dependencias se inyectan de afuera hacia adentro.

Para aplicar esta arquitectura se necesitaba inyectar las dependencias a cada capa lo cual se realizó primero de forma manual y luego se automatizó con Dagger Hilt eliminando la necesidad de utilizar una Factory para instanciar el ViewModel con parámetros.


## Capa UI / Data Source

Se implementó un Navigation Component para manejar los 3 Fragments en una única Activity y cada Fragment es una pantalla de la aplicación: 

- Pantalla Inicial de búsqueda
- Pantalla de visualización de resultados
- Pantalla con el detalle del ítem seleccionado

Navigation Component esta formado por:

- NavGraph: Colección de destinos con sus conexiones
- NavHost: Contenedor de los fragmentos (ubicado en el MainActivity)
- NavController: Clase que maneja la navegación del NavHost interactuando con el NavGraph

Para pasar la información a cada Fragment se utilizó el componente de Gradle "Safe Args", que nos provee seguridad de tipo, a diferencia del objeto Bundle.

Para la Data Source, en este caso se utiliza una llamada a la API de Mercadolibre pero también podría ser una base de datos.


## Capa Presenter/Repository

Se utilizó patrón MVVM, creando un ViewModel para el Fragment de resultados de búsqueda y otro para el Fragment de detalles del ítem. Se podría haber usado el mismo ViewModel perfectamente, pero si tuviésemos expectativas de ampliar las funcionalidades de cada pantalla sería mejor dejar los ViewModels seperados.

ViewModel utiliza LiveData, actualizando la UI automáticamente ante cambios en las variables previamente marcadas como observables.

Se crearon 2 repositorios, uno para ir a buscar la lista de ítems y otro para ir a buscar los datos específicos del ítem:

- ResultsRepository
- DetailsRepository


## Capa Use Cases

Para abstraer un grado más el ViewModel del repositorio de datos y evitar los “God ViewModels” se utilizan los casos de uso, donde se implementa la lógica del negocio. Esta capa puede ser considerada como la Capa de Servicio en una arquitectura de capas.

![layers](https://miro.medium.com/max/1064/1*CAdK7Eqcbaof4p-N_HHv8Q.png)

 El objetivo primario es separar las responsabilidades, en esta aplicación se definieron 2 casos de usos:
 
-	GetItemListUseCase: Solicita una lista de productos por nombre
-	GetItemDetailUseCase: Solicita los datos del ítem seleccionado


## Capa Entities

Se describen los objetos del negocio de nuestro sistema, en este caso se declararon las estructuras de datos que corresponden a los retornos de las llamadas al API de Mercadolibre.


## Flujo de datos

A continuación se describen los flujos de datos de la pantalla Resultados de Búsqueda y Detalle del producto:

![umlfragments](https://plantuml-server.kkeisuke.dev/svg/VPBF3e8m3CRlUueUuU0BS30O4ms9HcEYzm4DBW5creBnxHs8_7783ilMVDzlxTgGM1gkYbm8MR4kgo96Oyy9greomWMfodbdP5PWoM9wXTKQRWgVHvrY7a26734M1qNi-ID6Bm4n7ArrF-YlX5j9-Cle4QvKkA66-KdGxWzDYhLvzKYQvEJeX1DfS4VCrUOSGG24qYHt5zGfn5SvbuErjyOsLDhSKtJyy-_F5Bd0Selwyqj36nPeCty-xTzq609G3GcyKqneQZJ-6DORHRT2B5CxTcy0.svg)


![umldetail](https://plantuml-server.kkeisuke.dev/svg/hPF12i8m44Jl-nKvwi4Vy22AelGWYg3tQ4S9j8aaMyM_DxLgQr95mPoIxSpBTjWC7KlBHPuHoOADBl8jhD_lM9qaGqp0KcLJAmyvDDFhKVG6OgDmdfiKMLySm0aZdqYMdMw3yPG8ghVUEWDNFBTs64k7zuX8Ib6b95QL5Ox6AJRsKWDl7pmo-B5QIc6XvCsHgT4o4HyuTmazf2rTU7lffHIHg_NMbbn2VzJzht6udLL7LaR_8vC7xFTS6esqnVDbEcyetuLKZsGLSwBtfac9H-zbY8CNqHSgo7UnTR0ds6uJ-aHGjORGgN-4Lm00.svg)


## Testing

Se realizó tests unitarios en los ViewModels y en los casos de uso. Para la parte UI se probaron integraciones con las vistas.


Ing. Bruno Diaz
2021

