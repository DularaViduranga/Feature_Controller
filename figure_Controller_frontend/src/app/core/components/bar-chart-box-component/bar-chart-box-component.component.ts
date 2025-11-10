import { Component } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartOptions } from 'chart.js';

@Component({
  selector: 'app-bar-chart-box-component',
  standalone: true,
  imports: [BaseChartDirective],
  templateUrl: './bar-chart-box-component.component.html',
  styleUrl: './bar-chart-box-component.component.scss'
})
export class BarChartBoxComponentComponent {
  barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: ['Performance'],
    datasets: [
      { label: 'Target', data: [80], backgroundColor: '#3b82f6' },
      { label: 'Collections', data: [65], backgroundColor: '#10b981' }
    ]
  };
  barChartOptions: ChartOptions<'bar'> = {
    responsive: true,
    maintainAspectRatio: false,
  };
}
