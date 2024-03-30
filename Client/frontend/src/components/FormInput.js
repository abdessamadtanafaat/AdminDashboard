

const FormInput = ({type , label , name , placeholder, value, onChange , disabled }) => {
  return (
    <div className='form-control '>
      <label className='label'>
        <span className='label-text capitalize text-base font-semibold'>{label}</span>
      </label>
      <input
        type={type}
        name={name}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        className='input input-bordered input-secondary ' disabled={disabled}
      />
    </div>
  )
}

export default FormInput
