Write-Host "Compilando el proyecto..."

# Crear carpeta de salida si no existe
if (-not (Test-Path -Path "out")) {
    New-Item -ItemType Directory -Path "out"
}

# Compilar todos los archivos .java y dejar los .class en out/
javac -d out -cp libs/ice-3.7.10.jar `
    (Get-ChildItem -Recurse -Include *.java | ForEach-Object { $_.FullName })

Write-Host "Compilación completada."
