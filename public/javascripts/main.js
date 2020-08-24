var TableRows = {
    Number: 0,
    Brand: 1,
    Model: 2,
    Color: 3,
    Horses: 4,
    Owners: 5,
    Year: 6,
    Added: 7
};

function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchCount = 0;
    table = document.getElementById("carTable");
    switching = true;
    dir = "asc";
    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            if (dir === "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            } else if (dir === "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            switchCount ++;
        } else {
            if (switchCount === 0 && dir === "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}

function getStat() {
    var table = document.getElementById("carTable");
    var rows = table.getElementsByTagName("TR");

    var message = "Всего зарегистрировано автомобилей: " + countCars(rows) + '\n' +
        "Дата первой регистрации: " + getFirstDate(rows) + '\n' +
        "Дата последней регистрации: " + getLastDate(rows) + '\n' +
        "Самый популярный цвет: " + mode(makeArrFromTableRows(rows, TableRows.Color)) + '\n' +
        "Самая популярная марка авто: " + mode(makeArrFromTableRows(rows, TableRows.Brand));
    alert(message)
}

function countCars(rows) {
    var carCount = 0;
    for (var i = 0; i < rows.length; i++) {
        if(rows[i].getElementsByTagName("td").length > 0) carCount ++;
    }
    return carCount;
}

function getFirstDate(rows) {
    var min = "2030-0-0 0:0:0";
    for (var i = 1; i < (rows.length); i++) {
        var x = rows[i].getElementsByTagName("TD")[7];
        if(x.textContent < min) min = x.textContent
    }
    return min;
}

function getLastDate(rows) {
    var max = "2010-0-0 0:0:0";
    for (var i = 1; i < (rows.length); i++) {
        var x = rows[i].getElementsByTagName("TD")[7];
        if(max < x.textContent) max = x.textContent
    }
    return max;
}

function mode(array) {
    if (array.length === 0)
        return null;
    var modeMap = {};
    var maxEl = array[0], maxCount = 1;
    for (var i = 0; i < array.length; i++) {
        var el = array[i];
        if (modeMap[el] == null)
            modeMap[el] = 1;
        else
            modeMap[el]++;
        if (modeMap[el] > maxCount) {
            maxEl = el;
            maxCount = modeMap[el];
        }
    }
    return maxEl;
}

function makeArrFromTableRows(rows, column) {
    var array = [];
    for (var i = 1; i < (rows.length); i++) {
        var x = rows[i].getElementsByTagName("TD")[column];
        array[i - 1] = x.textContent
    }

    return array;
}