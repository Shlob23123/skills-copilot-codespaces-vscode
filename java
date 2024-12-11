// Initialize variables
let cash = 10000;
let reputation = 50;
let employees = 5;
let day = 1;
let income = 0;
let expenses = 0;

// Track cash trend for the chart
const cashHistory = [cash];
const daysHistory = [day];

// DOM elements
const dayElement = document.getElementById("day");
const cashElement = document.getElementById("cash");
const reputationElement = document.getElementById("reputation");
const employeesElement = document.getElementById("employees");
const incomeElement = document.getElementById("income");
const expensesElement = document.getElementById("expenses");
const eventMessageElement = document.getElementById("event-message");

// Setup Chart.js
const ctx = document.getElementById("financial-chart").getContext("2d");
const financialChart = new Chart(ctx, {
    type: "line",
    data: {
        labels: daysHistory,
        datasets: [
            {
                label: "Cash Trend",
                data: cashHistory,
                borderColor: "#00d4ff",
                backgroundColor: "rgba(0, 212, 255, 0.2)",
                borderWidth: 2,
                fill: true,
                tension: 0.3,
            },
        ],
    },
    options: {
        responsive: true,
        scales: {
            x: { title: { display: true, text: "Day" } },
            y: { title: { display: true, text: "Cash ($)" } },
        },
    },
});

// Random events
function randomEvent() {
    const events = [
        { message: "Tech Breakthrough! Reputation +10", cashChange: 0, reputationChange: 10 },
        { message: "Market Crash! Cash -3000", cashChange: -3000, reputationChange: 0 },
        { message: "Employee Strike! Employees -1", cashChange: 0, reputationChange: 0, employeesChange: -1 },
        { message: "Marketing Success! Cash +5000", cashChange: 5000, reputationChange: 0 }
    ];
    const event = events[Math.floor(Math.random() * events.length)];
    cash += event.cashChange;
    reputation += event.reputationChange;
    if (event.employeesChange) {
        employees += event.employeesChange;
    }
    return event.message;
}

// Update stats on the page
function updateStats() {
    dayElement.textContent = `Day: ${day}`;
    cashElement.textContent = `Cash: $${cash}`;
    reputationElement.textContent = `Reputation: ${reputation}`;
    employeesElement.textContent = `Employees: ${employees}`;
    incomeElement.textContent = `Income: $${income}`;
    expensesElement.textContent = `Expenses: $${expenses}`;

    // Update chart
    cashHistory.push(cash);
    daysHistory.push(day);
    financialChart.update();
}

// Actions
document.getElementById("invest-btn").addEventListener("click", () => {
    if (cash >= 2000) {
        cash -= 2000;
        reputation += 5;
        income += 5000;
    }
    updateStats();
});

document.getElementById("hire-btn").addEventListener("click", () => {
    if (cash >= 1000) {
        cash -= 1000;
        employees += 1;
        reputation += 3;
    }
    updateStats();
});

document.getElementById("upgrade-btn").addEventListener("click", () => {
    if (cash >= 5000) {
        cash -= 5000;
        reputation += 10;
        income += 10000;
    }
    updateStats();
});

// Game loop (simulate daily updates)
setInterval(() => {
    day += 1;
    expenses = employees * 500;
    cash += income - expenses;

    if (day % 10 === 0) {
        const eventMessage = randomEvent();
        eventMessageElement.textContent = eventMessage;
        setTimeout(() => (eventMessageElement.textContent = ""), 3000);
    }

    if (cash <= 0) {
        alert("Game Over! You ran out of cash.");
        window.location.reload();
    }

    updateStats();
}, 2000);

// Initialize stats
updateStats();