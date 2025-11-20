import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientManagementComponent } from './client-management/client-management.component';

const routes: Routes = [
  { path: '', redirectTo: '/clients', pathMatch: 'full' },
  { path: 'clients', component: ClientManagementComponent },
  { path: '**', redirectTo: '/clients' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }