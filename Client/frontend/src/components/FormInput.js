

const FormInput = ({type , label , name , placeholder, value, onChange , disabled }) => {
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
        onChange={onChange}
        className='input input-bordered input-accent'
        disabled={disabled}
      />
    </div>
  )
}

export default FormInput
