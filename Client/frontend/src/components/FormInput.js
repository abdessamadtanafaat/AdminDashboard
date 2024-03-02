

const FormInput = ({type , label , name , placeholder }) => {
  return (
    <div className='form-control '>
      <label className='label'>
        <span className='label-text capitalize'>{label}</span>
      </label>
      <input
        type={type}
        name={name}
        placeholder={placeholder}
        className='input input-bordered '
      />
    </div>
  )
}

export default FormInput
