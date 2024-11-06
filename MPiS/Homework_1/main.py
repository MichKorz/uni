from fun import *
import random
import matplotlib.pyplot as plt


funs = [third_root_of_x, sin_x, polynomial]

ranges_x = [(0, 8), (0, math.pi), (0, 1)] 
ranges_y = [(0, 2), (0, 1), (0, 27/64)]

batch_counts = [5, 50]
sample_counts = list(range(50, 5001, 50))

correct_values = [12, 2, 0.2]

ex_y = list(list(range(20)) for _ in range(50, 5001, 50))

for task in range(3):
    x_min = ranges_x[task][0]
    x_max = ranges_x[task][1]

    y_min = ranges_y[task][0]
    y_max = ranges_y[task][1]

    for batch_count in batch_counts:
        plt.title(funs[task].__name__)
        plt.axhline(correct_values[task], color = "green")

        for sample_count in sample_counts:
            approx_sum = 0
            for batch in range(batch_count):
                points_under = 0
                area = (x_max - x_min) * (y_max - y_min)
                for sample in range(sample_count):
                    x = random.uniform(x_min, x_max)
                    y = random.uniform(y_min, y_max)
                    if y <= funs[task](x):
                        points_under += 1
                approx_value = points_under/sample_count * area
                approx_sum += approx_value
                plt.scatter(sample_count, approx_value, s = 5, alpha = .3, color = "blue")
            approx_average = approx_sum/batch_count
            plt.scatter(sample_count, approx_average, s = 5, color = "red")

        name = ''.join([funs[task].__name__, '_', str(batch_count), '.png'])
        plt.savefig(name, dpi=500)
        plt.clf()
    

