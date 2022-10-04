# # R2-D2 y las Lunas de Endor - Acceso a Datos

Nuestro amigo R2-D2 ha sido enviado junto a Luke Skywalker debido a una serie de problemas relacionados con la contaminación en la Luna de Endor. Problema de procesamiento de datos.

[![Kotlin](https://img.shields.io/badge/Code-Kotlin-blueviolet)](https://kotlinlang.org/)
[![LICENSE](https://img.shields.io/badge/License-CC-%23e64545)](https://joseluisgs.github.io/docs/license/)
![GitHub](https://img.shields.io/github/last-commit/joseluisgs/R2D2-Endor-AccesoDatos-Kotlin)

![imagen](https://cdnb.artstation.com/p/assets/images/images/016/922/017/large/serhii-sirenko-r2-art-3.jpg)

- [# R2-D2 y las Lunas de Endor - Acceso a Datos](#-r2-d2-y-las-lunas-de-endor---acceso-a-datos)
  - [Enunciado](#enunciado)
  - [Autor](#autor)
    - [Contacto](#contacto)
    - [¿Un café?](#un-café)
  - [Licencia de uso](#licencia-de-uso)

## Enunciado
Nuestro amigo R2-D2 ha sido enviado junto a Luke Skywalker debido a una serie de problemas relacionados con la contaminación en la Luna de Endor. Es por ello que mientras Luke se queda investigando, nuestro gran “Arturito” ha usado todos sus sensores para obtener datos del entorno y enviarlos al Halcón Milenario para ser procesados.

Para ello R2-D2 ha tomado muestras en dos ficheros. Data03.csv y Data03.xml. Pero se ha dado cuenta que el primero de ellos debido a una tormenta eléctrica ha quedado corrupto, por lo que debe completar las mediciones antes de procesarlas con el segundo, solo si esa muestra no existe en base a su identificador.

De las muestras nos interesan solamente:

- Identifier: identificador de la muestra
- Modified: Fecha de la medición
- NO2: concentración de dióxido de azufre
- Temperature: temperatura
- CO: concentración de carbono
- Ozone: concentración de ozono

Todas estas mediciones las meterá en una lista de memoria para poder procesarlas. Para leer el XML se recomienda automatizar el proceso con un parser.

Una vez que ha cargado todos los datos en memoria, R2-D2 procesa los datos usando su IA para hacer un informe (¡¡¡R2-D2 es la leche para eso!!!). Este procesamiento lo vamos a hacer en lotes de 25 (de 25 en 25) realizando los cálculos necesarios en esos 25 registros y salvándolos en registro de estadísticas.

Por cada estadística obtendremos una serie de resúmenes. Este resumen está formado por:
- Nivel máximo, mínimo y media de NO2 y la fecha en la que se registró el máximo y mínimo.
- Nivel máximo, mínimo y media de  Temperatura y la fecha en la que se registró el máximo y mínimo.
- Nivel máximo, mínimo y media de Ozono y la fecha en la que se registró el máximo y mínimo.

Cada informe junto a sus estadística y resúmenes que la forman deben tener su identificador y fecha. 

Finalmente debemos exportar el informe completo con todos lo mediciones (una por cada lote) y cada una de los resúmenes que la forman a un fichero XML y JSON para facilitar su tratamiento desde el Halcón Milenario.

Finalmente, R2-D2 nos mostrará en fichero tipo Markdown llamado informe.md pues parece ser que son los causantes de los problemas detectados.

## Autor

Codificado con :sparkling_heart: por [José Luis González Sánchez](https://twitter.com/joseluisgonsan).

[![Twitter](https://img.shields.io/twitter/follow/joseluisgonsan?style=social)](https://twitter.com/joseluisgonsan)
[![GitHub](https://img.shields.io/github/followers/joseluisgs?style=social)](https://github.com/joseluisgs)

Partner de Formación Kotlin certificado por JetBrains.

 <a href="https://www.jetbrains.com/es-es/company/partners/kotlin/" target="_blank"> 
    <img loading="lazy" style="border-radius: 0.25rem;" 
      src="https://i.imgur.com/Ca7Yu1B.png" alt="Log1" height="100"
      borderRadius='1rem' boxShadow = '0 5px 18px rgba(0,0,0,0.3)'>
  </a> &nbsp;

### Contacto

<p>
  Cualquier cosa que necesites házmelo saber por si puedo ayudarte 💬.
</p>
<p>
 <a href="https://joseluisgs.github.io/" target="_blank">
        <img src="https://joseluisgs.github.io/img/favicon.png" 
    height="30">
    </a>  &nbsp;&nbsp;
    <a href="https://github.com/joseluisgs" target="_blank">
        <img src="https://distreau.com/github.svg" 
    height="30">
    </a> &nbsp;&nbsp;
        <a href="https://twitter.com/joseluisgonsan" target="_blank">
        <img src="https://i.imgur.com/U4Uiaef.png" 
    height="30">
    </a> &nbsp;&nbsp;
    <a href="https://www.linkedin.com/in/joseluisgonsan" target="_blank">
        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/LinkedIn_logo_initials.png/768px-LinkedIn_logo_initials.png" 
    height="30">
    </a>  &nbsp;&nbsp;
    <a href="https://discordapp.com/users/joseluisgs#3560" target="_blank">
        <img src="https://logodownload.org/wp-content/uploads/2017/11/discord-logo-4-1.png" 
    height="30"> 
    </a> &nbsp;&nbsp;
    <a href="https://g.dev/joseluisgs" target="_blank">
        <img loading="lazy" src="https://googlediscovery.com/wp-content/uploads/google-developers.png" 
    height="30">
    </a>    
</p>

### ¿Un café?

<p><a href="https://www.buymeacoffee.com/joseluisgs"> <img align="left" src="https://cdn.buymeacoffee.com/buttons/v2/default-blue.png" height="48" alt="joseluisgs" /></a></p><br><br><br>

## Licencia de uso

Este repositorio y todo su contenido está licenciado bajo licencia **Creative Commons**, si desea saber más, vea
la [LICENSE](https://joseluisgs.github.io/docs/license/). Por favor si compartes, usas o modificas este proyecto cita a
su autor, y usa las mismas condiciones para su uso docente, formativo o educativo y no comercial.

<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Licencia de Creative Commons" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">
JoseLuisGS</span>
by <a xmlns:cc="http://creativecommons.org/ns#" href="https://joseluisgs.github.io/" property="cc:attributionName" rel="cc:attributionURL">
José Luis González Sánchez</a> is licensed under
a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons
Reconocimiento-NoComercial-CompartirIgual 4.0 Internacional License</a>.<br />Creado a partir de la obra
en <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/joseluisgs" rel="dct:source">https://github.com/joseluisgs</a>
.