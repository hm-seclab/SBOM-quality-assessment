
export function generateCoverageReportPlot(fulldata, mode) {
    const data = fulldata.filter(i => i.mode === mode)

    const result = {
        chartData: {
            datasets: [],
        },
        chartOptions: {
            plugins: {
                title: {
                    display: true,
                    text: mode
                }
            },
            responsive: true,
            aspectRatio: 1,
            maintainAspectRatio: false,
            scales: {
                x: {
                    stacked: true,
                },
                y: {
                    stacked: true,
                },
            },
        },
    }

    const generators = new Set()
    const statusSet = new Set()
    for (const item of data) {
        generators.add(item.generator)
        statusSet.add((item.status))
    }

    for (const stat of Array.from((statusSet)).sort()) {
        const dat = []
        for (const gen of Array.from(generators).sort()) {
            dat.push(data.filter(i => i.generator === gen && i.status === stat).length)
        }
        result.chartData.datasets.push({
            label: stat,
            backgroundColor: retrieveStatusColorCodePlot(stat),
            data: dat
        })
    }


    result.chartData.labels = Array.from(generators).sort()
    return result;
}

function retrieveStatusColorCodePlot(status) {
    if (status === 'good') {
        return 'rgba(120,224,109,0.5)'
    } else if (status === 'corrupted') {
        return 'rgba(246,227,79,0.5)'
    } else if (status === 'failed') {
        return 'rgba(255, 99, 132, 0.5)'
    } else {
        return 'rgba(70,70,70,0.5)'
    }
}