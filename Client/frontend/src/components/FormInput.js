import {EyeIcon , EyeOffIcon} from 'lucide-react'
import {useState} from 'react'
const FormInput = ({type , label , name , placeholder, value, onChange ,  disabled , register, error}) => {
  const [passwordVisible, setPasswordVisible] = useState(false);

  const togglePasswordVisibility = () => {
      
      setPasswordVisible(!passwordVisible);
  };


  return (
    <div className="flex items-center">
      <div className='form-control grow relative'>
        <label className='label'>
        <span className='label-text capitalize font-semibold'>{label}</span>
      </label>
      <input
        type={type !=="password" ? type : passwordVisible? "text" :type}
        name={name}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        className={`input grow input-bordered input-accent ${error ? 'input-error' : ''}`}
        disabled={disabled}
        {...(register ? register(name, { required: true }) : {})} 
      />
            {error && <p className="text-red-500">{label} is required</p>}
      </div>
      {type!=="password" || (
        <div className="relative my-auto" style={{ marginTop: '50px' }}>
        <button
          type="button"
          className="focus:outline-none -ml-10"
          onClick={togglePasswordVisibility}>
            {passwordVisible ?  <EyeIcon  /> : <EyeOffIcon /> }
        </button>
      </div>
      )}
    </div>  
   
  )
}

export default FormInput
