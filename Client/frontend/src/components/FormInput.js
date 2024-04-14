

const FormInput = ({type , label , name , placeholder, value, disabled , register, error}) => {

  return (
    <div className='form-control '>
      <label className='label'>
        <span className='label-text capitalize font-semibold'>{label}</span>
      </label>
      <input
        type={type}
        name={name}
        placeholder={placeholder}
        value={value}
        className={`input input-bordered input-accent ${error ? 'input-error' : ''}`}
        disabled={disabled}
        {...(register ? register(name, { required: true }) : {})} 
      />
            {error && <p className="text-red-500">{label} is required</p>}
    </div>
  )
}

export default FormInput
