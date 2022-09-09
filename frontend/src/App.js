import React from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import {LoginView} from "./LoginView";
import {DentistView} from "./DentistView";
import {PatientView} from "./PatientView";
function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<LoginView/>}/>
          <Route path="/appUser/:id" element={<PatientView/>}/>
          <Route path="/dentist" element={<DentistView/>}/>
        </Routes>
      </Router>
  )
}

export default App;
