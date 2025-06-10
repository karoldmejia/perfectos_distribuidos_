// Perfectos.ice
// Define las interfaces para Worker, Master y Client usando Ice correctamente.

module Perfectos {

    // Alias para una lista de números perfectos
    sequence<long> ListaPerfectos;

    interface Worker {
        ListaPerfectos buscarPerfectos(long inicio, long fin, string idTarea);
    };

    interface Master {
        void recibirResultado(string idTarea, ListaPerfectos perfectos);
        void findPerfectNumbers(long inicio, long fin, string idTarea);
    };

    interface Client {
        void mostrarResultado(ListaPerfectos perfectos, string idTarea);
    };
};
