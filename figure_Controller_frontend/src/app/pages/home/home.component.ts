import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { NavBarComponentComponent } from '../../core/components/nav-bar-component/nav-bar-component.component';
import { TopBarComponentComponent } from '../../core/components/top-bar-component/top-bar-component.component';
import { BarChartBoxComponentComponent } from '../../core/components/bar-chart-box-component/bar-chart-box-component.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    NavBarComponentComponent,
    TopBarComponentComponent,
    BarChartBoxComponentComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  animations: [
    trigger('slideInOut', [
      state('in', style({ transform: 'translateX(0%)' })),
      state('out', style({ transform: 'translateX(-100%)' })),
      transition('out => in', [animate('300ms ease-in')]),
      transition('in => out', [animate('300ms ease-out')])
    ])
  ]
})
export class HomeComponent {
  sidebarOpen = false;
  
  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }
}
