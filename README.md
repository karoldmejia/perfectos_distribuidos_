# Proyecto de distribuidos – Cliente, Workers y Master

Los scripts de automatización están configurados para funcionar **exclusivamente en Windows PowerShell**.  

---

## Compilación

Antes de compilar el proyecto:

1. Abre una terminal de PowerShell.
2. Navega a la **raíz del proyecto** (donde se encuentra el archivo `build.ps1`).
3. Ejecuta el siguiente comando:

```powershell
.\build.ps1
````

Esto compilará las clases Java necesarias y generará los archivos `.class` en la carpeta `out`.

---

## Ejecución

Una vez compilado, se puede ejecutar el sistema completo (cliente + master + workers) con:

```powershell
.\run.ps1 -numeroWorkers 2 -rangoInicio 1 -rangoFin 30000
```

### Parámetros

* `-numeroWorkers`: número de workers que se lanzarán (por defecto, 2).
* `-rangoInicio`: inicio del rango de búsqueda.
* `-rangoFin`: fin del rango de búsqueda.

Cada worker se lanzará en una nueva ventana de PowerShell y se le asignará un puerto consecutivo a partir del **10003**.

---

## Importante: Configurar manualmente los workers en `MasterServer.java`

El archivo `MasterServer.java` tiene actualmente una cantidad fija de workers configurada en un bucle como el siguiente:

```java
for (int i = 0; i < 2; i++) {
    int port = 10003 + i; // puertos: 10003, 10004, ...
    String proxyString = "worker" + i + ":default -h 127.0.0.1 -p " + port;
    WorkerPrx workerProxy = WorkerPrx.checkedCast(communicator.stringToProxy(proxyString));

    if (workerProxy == null) {
        System.err.println("No se pudo conectar al worker en puerto " + port);
        return;
    }

    workers.add(workerProxy);
}
```

Si se cambia el número de workers en `run.ps1`, **también se debe modificar este valor (`i < ...`) en el código Java** para que coincida.
Por defecto está en `2`.

---
