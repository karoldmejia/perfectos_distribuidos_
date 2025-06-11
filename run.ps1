param (
    [int]$numeroWorkers = 3,
    [int]$rangoInicio = 1,
    [int]$rangoFin = 10000
)

$puertoInicial = 10003

Write-Host "Iniciando Client..."
Start-Process -WindowStyle Normal -FilePath "cmd.exe" -ArgumentList '/k', 'java -cp out;libs/ice-3.7.10.jar client.ClientServer'

Write-Host "Iniciando $numeroWorkers workers (puerto inicial fijo: $puertoInicial)..."
for ($i = 0; $i -lt $numeroWorkers; $i++) {
    $nombreWorker = "worker$i"
    $puerto = $puertoInicial + $i
    Write-Host "→ Iniciando $nombreWorker en puerto $puerto"
    Start-Process -WindowStyle Normal -FilePath "cmd.exe" -ArgumentList '/k', "java -cp out;libs/ice-3.7.10.jar worker.WorkerServer $nombreWorker $puerto"
    Start-Sleep -Milliseconds 500
}

Write-Host "Iniciando Master..."
Start-Process -WindowStyle Normal -FilePath "cmd.exe" -ArgumentList '/k', 'java -cp out;libs/ice-3.7.10.jar master.MasterServer'

Write-Host "`n Iniciando cliente con rango de búsqueda $rangoInicio a $rangoFin..."
Start-Process -WindowStyle Normal -FilePath "cmd.exe" -ArgumentList '/k', "java -cp out;libs/ice-3.7.10.jar client.Launcher $rangoInicio $rangoFin"
