from funs import * 
import random 
import matplotlib.pyplot as plt  

# Lista funkcji do analizy
funs = [third_root_of_x, sin_x, polynomial, pi]

# Przedziały dla wartości x, y dla poszczególnych funkcji
ranges_x = [(0, 8), (0, math.pi), (0, 1),     (0, 2)] 
ranges_y = [(0, 2), (0, 1),       (0, 27/64), (0, 2)]

# Ilość powtórzeń w każdej serii (k)
batch_counts = [5, 50]

# Liczba próbek (n) (od 50 do 5000 z krokiem 50)
sample_counts = list(range(50, 5001, 50))

# Poprawne wartości wyników dla poszczególnych funkcji
correct_values = [12, 2, 0.2, math.pi]

# Główna pętla iterująca przez każdą z funkcji
for task in range(4):
    x_min = ranges_x[task][0]
    x_max = ranges_x[task][1]
    y_min = ranges_y[task][0]
    y_max = ranges_y[task][1]

    # Iteracja przez ilość powtórzeń w serii
    for batch_count in batch_counts:
        
        # Tworzenie nazwy wykresu opartej na nazwie funkcji i liczbie powtórzeń w serii
        name = ''.join([funs[task].__name__, '_', str(batch_count)])
        plt.title(name)  # Ustawianie tytułu wykresu
        plt.axhline(correct_values[task], color="green")  # Dodanie linii referencyjnej dla poprawnej wartości

        # Iteracja przez różne liczby próbek w powtórzeniu
        for sample_count in sample_counts:
            approx_sum = 0  # Inicjalizacja zmiennej do sumowania przybliżonych wartości

            # Iteracja przez serie (powtórzenia dla uśrednienia wyników)
            for batch in range(batch_count):
                points_under = 0  # Licznik punktów poniżej wykresu funkcji
                area = (x_max - x_min) * (y_max - y_min)  # Obliczenie powierzchni badanego obszaru

                # Iteracja przez liczbę próbek w danej serii
                for sample in range(sample_count):
                    x = random.uniform(x_min, x_max) 
                    y = random.uniform(y_min, y_max) 
                    if funs[task](x, y):  # Sprawdzanie, czy punkt jest poniżej wykresu funkcji
                        points_under += 1

                # Obliczenie przybliżonej wartości dla danej serii
                approx_value = points_under / sample_count * area
                approx_sum += approx_value 
                plt.scatter(sample_count, approx_value, s=3, alpha=.3, color="blue")

            # Obliczenie średniej przybliżonej wartości dla danej ilości próbek
            approx_average = approx_sum / batch_count
            plt.scatter(sample_count, approx_average, s=7, color="red")

        # Zapisanie wykresu do pliku
        name = ''.join([name, '.png'])
        plt.savefig(name, dpi=500)
        plt.clf()  # Czyszczenie wykresu przed kolejną iteracją
