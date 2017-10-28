# Output
set datafile separator ',' # csv separator
set terminal png enhanced font "arial,11" fontscale 1.3
set output 'img/s_mis.png'


# Legend
set key right top


# Labels
set xlabel 'Nombre de points'
set ylabel 'Taille du MCDS '

# Plot
plot 'benchmark.csv' using  1:2 title columnheader with linespoints